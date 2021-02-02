import React, { Component } from "react";
import {
  Grid,
  IconButton,
  Icon,
  TablePagination,
  Button,
  TextField,
  FormControl,
  Input,
  InputAdornment,
} from "@material-ui/core";
import moment from "moment";
import MaterialTable, {
  MTableToolbar,
  Chip,
  MTableBody,
  MTableHeader,
} from "material-table";
import { Breadcrumb, ConfirmationDialog } from "egret";
import { useTranslation, withTranslation, Trans } from "react-i18next";
import shortid from "shortid";
import { saveAs } from "file-saver";
import { Helmet } from "react-helmet";
import { makeStyles, withStyles } from "@material-ui/core/styles";
import SearchIcon from "@material-ui/icons/Search";
import Tooltip from "@material-ui/core/Tooltip";
import { ValidatorForm, TextValidator } from "react-material-ui-form-validator";
import AsynchronousAutocomplete from "../utilities/AsynchronousAutocomplete";
import { Link } from "react-router-dom";
import { searchByPage as searchStore } from "../Kho/KhoService";
import NotificationPopup from "../Component/NotificationPopup/NotificationPopup";
// import { saveAs } from 'file-saver';
import { isThisSecond } from "date-fns/esm";
// import PhieuNhapKhoDialog from "./PhieuNhapKhoDialog";
import { searchByPage, exportToExcel } from "./BaoCaoXuatService";
import {
  MuiPickersUtilsProvider,
  DateTimePicker,
  KeyboardTimePicker,
  KeyboardDatePicker,
} from "@material-ui/pickers";
import DateFnsUtils from "@date-io/date-fns";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

toast.configure({
  autoClose: 2000,
  draggable: false,
  limit: 3,
});

const LightTooltip = withStyles((theme) => ({
  tooltip: {
    backgroundColor: theme.palette.common.white,
    color: "rgba(0, 0, 0, 0.87)",
    boxShadow: theme.shadows[1],
    fontSize: 11,
    marginLeft: "-1.5em",
  },
}))(Tooltip);

function MaterialButton(props) {
  const { t, i18n } = useTranslation();
  const item = props.item;
  return (
    <div className="none_wrap">
      <LightTooltip
        title={t("general.editIcon")}
        placement="right-end"
        enterDelay={300}
        leaveDelay={200}
        PopperProps={{
          popperOptions: {
            modifiers: { offset: { enabled: true, offset: "10px, 0px" } },
          },
        }}
      >
        <IconButton size="small" onClick={() => props.onSelect(item, 0)}>
          <Icon fontSize="small" color="primary">
            edit
          </Icon>
        </IconButton>
      </LightTooltip>
      <LightTooltip
        title={t("general.deleteIcon")}
        placement="right-end"
        enterDelay={300}
        leaveDelay={200}
        PopperProps={{
          popperOptions: {
            modifiers: { offset: { enabled: true, offset: "10px, 0px" } },
          },
        }}
      >
        <IconButton size="small" onClick={() => props.onSelect(item, 1)}>
          <Icon fontSize="small" color="error">
            delete
          </Icon>
        </IconButton>
      </LightTooltip>
    </div>
  );
}

class BaoCaoXuat extends React.Component {
  state = {
    keyword: "",
    rowsPerPage: 10,
    page: 0,
    MaintainRequestStatus: [],
    item: {},
    shouldOpenEditorDialog: false,
    shouldOpenConfirmationDialog: false,
    selectAllItem: false,
    selectedList: [],
    totalElements: 0,
    shouldOpenConfirmationDeleteAllDialog: false,
    shouldOpenConfirmationDeleteListDialog: false,
    shouldOpenNotificationPopup: false,
    Notification: "",
    toDate: moment().endOf("month"),
    fromDate: moment().startOf("month"),
    kho: null,
    khoId: null
  };
  numSelected = 0;
  rowCount = 0;

  handleTextChange = (event) => {
    this.setState({ keyword: event.target.value }, function () {});
  };

  handleKeyDownEnterSearch = (e) => {
    if (e.key === "Enter") {
      this.search();
    }
  };

  setPage = (page) => {
    this.setState({ page }, function () {
      this.updatePageData();
    });
  };

  setRowsPerPage = (event) => {
    this.setState({ rowsPerPage: event.target.value, page: 0 }, function () {
      this.updatePageData();
    });
  };

  handleChangePage = (event, newPage) => {
    this.setPage(newPage);
  };

  search() {
    this.setState({ page: 0 }, function () {
      var searchObject = {};
      searchObject.keyword = this.state.keyword;
      searchObject.pageIndex = this.state.page + 1;
      searchObject.pageSize = this.state.rowsPerPage;
      searchObject.fromDate = this.state.fromDate
        ? this.state.fromDate
        : new Date();
      searchObject.toDate = this.state.toDate ? this.state.toDate : new Date();
      searchByPage(searchObject)
        .then((res) => {
          this.setState({
            itemList: [...res.data],
          });
        })
        .catch((err) => {
          console.log(err);
        });
    });
  }
  checkData = () => {
    let { t } = this.props;
    if (!this.data || this.data.length === 0) {
      toast.warning(t("general.noti_check_data"));
    } else if (this.data.length === this.state.itemList.length) {
      this.setState({ shouldOpenConfirmationDeleteAllDialog: true });
    } else {
      this.setState({ shouldOpenConfirmationDeleteListDialog: true });
    }
  };

  updatePageData = () => {
    var searchObject = {};
    if (this.state.khoId) {
      searchObject.khoId = this.state.khoId;
    }
    searchObject.keyword = this.state.keyword;
    searchObject.pageIndex = this.state.page + 1;
    searchObject.pageSize = this.state.rowsPerPage;
    searchObject.fromDate = this.state.fromDate
      ? this.state.fromDate
      : new Date();
    searchObject.toDate = this.state.toDate ? this.state.toDate : new Date();
    searchByPage(searchObject).then((res) => {
      this.setState({
        itemList: [...res.data],
      });
    });
  };

  handleDownload = () => {
    var blob = new Blob(["Hello, world!"], {
      type: "text/plain;charset=utf-8",
    });
    saveAs(blob, "hello world.txt");
  };
  handleDialogClose = () => {
    this.setState({
      shouldOpenEditorDialog: false,
      shouldOpenConfirmationDialog: false,
      shouldOpenConfirmationDeleteAllDialog: false,
      shouldOpenConfirmationDeleteListDialog: false,
      shouldOpenNotificationPopup: false,
    });
  };

  handleOKEditClose = () => {
    this.setState({
      shouldOpenEditorDialog: false,
      shouldOpenConfirmationDialog: false,
    });
    this.updatePageData();
  };

  handleDeleteMaintainRequestStatus = (id) => {
    this.setState({
      id,
      shouldOpenConfirmationDialog: true,
    });
  };

  componentDidMount() {
    this.updatePageData();
  }

  handleEditItem = (item) => {
    this.setState({
      item: item,
      shouldOpenEditorDialog: true,
    });
  };

  handleClick = (event, item) => {
    let { MaintainRequestStatus } = this.state;
    if (item.checked == null) {
      item.checked = true;
    } else {
      item.checked = !item.checked;
    }
    var selectAllItem = true;
    for (var i = 0; i < MaintainRequestStatus.length; i++) {
      if (
        MaintainRequestStatus[i].checked == null ||
        MaintainRequestStatus[i].checked == false
      ) {
        selectAllItem = false;
      }
      if (MaintainRequestStatus[i].id == item.id) {
        MaintainRequestStatus[i] = item;
      }
    }
    this.setState({
      selectAllItem: selectAllItem,
      MaintainRequestStatus: MaintainRequestStatus,
    });
  };

  handleSelectAllClick = (event) => {
    let { MaintainRequestStatus } = this.state;
    for (var i = 0; i < MaintainRequestStatus.length; i++) {
      MaintainRequestStatus[i].checked = !this.state.selectAllItem;
    }
    this.setState({
      selectAllItem: !this.state.selectAllItem,
      MaintainRequestStatus: MaintainRequestStatus,
    });
  };

  handleDelete = (id) => {
    this.setState({
      id,
      shouldOpenConfirmationDialog: true,
    });
  };

  handleDeleteAll = (event) => {
    //alert(this.data.length);
    this.handleDeleteList(this.data)
      .then(() => {
        this.updatePageData();
        // this.handleDialogClose();
        this.data = null;
      })
      .catch((err) => {
        console.log("loi");
      });
  };
  handleChangeDate = (date, name) => {
    this.setState({ [name]: date }, () => {
      this.search();
    });
  };
  exportToExcel = () => {
    const { t } = this.props;
    var searchObject = {};
    searchObject.keyword = this.state.keyword;
    searchObject.pageIndex = this.state.page + 1;
    searchObject.pageSize = this.state.rowsPerPage;
    searchObject.fromDate = this.state.fromDate
      ? this.state.fromDate
      : new Date();
    searchObject.toDate = this.state.toDate ? this.state.toDate : new Date();
    if (this.state.itemList == null || this.state.itemList.length == 0) {
      toast.warn("Không có dữ liệu");
      return;
    }
    exportToExcel(searchObject)
      .then((res) => {
        let blob = new Blob([res.data], {
          type:
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
        });
        saveAs(blob, "BaoCaoXuat.xlsx");
      })
      .catch((err) => {
        // console.log(err)
      });
  };
  selectKho = (item) => {
    if (item && Object.keys(item).length > 0) {
      this.setState({ khoId: item.id, kho: item }, () => {
        this.updatePageData();
      });
    } else {
      this.setState({ khoId: null, kho: null }, () => {
        this.updatePageData();
      });
    }
  };
  render() {
    const { t, i18n } = this.props;
    let {
      keyword,
      rowsPerPage,
      page,
      totalElements,
      itemList,
      item,
      shouldOpenConfirmationDialog,
      shouldOpenEditorDialog,
      shouldOpenConfirmationDeleteAllDialog,
      shouldOpenNotificationPopup,
      kho
    } = this.state;
    let TitlePage = t("Báo cáo xuất kho");
    let SearchObject = { pageIndex: 1, pageSize: 100000 };
    let columns = [
      {
        title: t("general.code"),
        field: "maSP",
        width: "150",
      },
      {
        title: t("general.name"),
        field: "tenSP",
        width: "150",
      },
      {
        title: t("Kho"),
        field: "tenKho",
        width: "150",
      },
      {
        title: t("Số lượng"),
        field: "soLuong",
        width: "150",
      },
      // {
      //   title: t("Người nhập"), field: "nguoiNhap.displayName", width: "150"
      // },
    ];
    return (
      <div className="m-sm-30">
        <Helmet>
          <title>
            {TitlePage} | {t("web_site")}
          </title>
        </Helmet>
        <div className="mb-sm-30">
          {/* <Breadcrumb routeSegments={[{ name: t('maintainRequestStatus.title') }]} /> */}
          <Breadcrumb
            routeSegments={[
              {
                name: t("Dashboard.category"),
                path: "/list/maintain_request_status",
              },
              { name: TitlePage },
            ]}
          />
        </div>

        <Grid container spacing={2} justify="space-between">
          <Grid item md={2} xs={12}>
            <Button
              className="mt-16 align-bottom"
              variant="contained"
              color="primary"
              onClick={this.exportToExcel}
            >
              Xuất excel
            </Button>
            {/* <Button
              className="mb-16 mr-16 align-bottom"
              variant="contained"
              color="primary"
              onClick={() => {
                this.handleEditItem({
                  startDate: new Date(),
                  endDate: new Date(),
                });
              }}
            >
              {t("general.add")}
            </Button>
            <Button
              className="mb-16 mr-36 align-bottom"
              variant="contained"
              color="primary"
              // onClick={() => this.setState({ shouldOpenConfirmationDeleteAllDialog: true })}
              onClick={() => this.checkData()}
            >
              {t("general.delete")}
            </Button> */}
          </Grid>
          <Grid item md={3} sm={12} xs={12}>
            <MuiPickersUtilsProvider utils={DateFnsUtils}>
              <KeyboardDatePicker
                label={
                  <span>
                    <span style={{ color: "red" }}></span>Từ ngày
                  </span>
                }
                className="w-100"
                disableToolbar
                format="dd/MM/yyyy"
                margin="normal"
                id="date-picker-inline"
                name="fromDate"
                value={this.state.fromDate}
                onChange={(date) => this.handleChangeDate(date, "fromDate")}
                KeyboardButtonProps={{
                  "aria-label": "change date",
                }}
                inputVariant="outlined"
                size="small"
              />
            </MuiPickersUtilsProvider>
          </Grid>
          <Grid item md={3} sm={12} xs={12}>
            <MuiPickersUtilsProvider utils={DateFnsUtils}>
              <KeyboardDatePicker
                label={
                  <span>
                    <span style={{ color: "red" }}></span>Đến ngày
                  </span>
                }
                className="w-100"
                disableToolbar
                format="dd/MM/yyyy"
                margin="normal"
                id="date-picker-inline"
                name="toDate"
                value={this.state.toDate}
                onChange={(date) => this.handleChangeDate(date, "toDate")}
                KeyboardButtonProps={{
                  "aria-label": "change date",
                }}
                inputVariant="outlined"
                size="small"
              />
            </MuiPickersUtilsProvider>
          </Grid>
          {shouldOpenNotificationPopup && (
            <NotificationPopup
              title={t("general.noti")}
              open={shouldOpenNotificationPopup}
              onYesClick={this.handleDialogClose}
              text={t(this.state.Notification)}
              agree={t("general.agree")}
            />
          )}

          {shouldOpenConfirmationDeleteAllDialog && (
            <ConfirmationDialog
              open={shouldOpenConfirmationDeleteAllDialog}
              onConfirmDialogClose={this.handleDialogClose}
              onYesClick={this.handleDeleteAll}
              text={t("general.deleteAllConfirm")}
              agree={t("general.agree")}
              cancel={t("general.cancel")}
            />
          )}
          {this.state.shouldOpenConfirmationDeleteListDialog && (
            <ConfirmationDialog
              open={this.state.shouldOpenConfirmationDeleteListDialog}
              onConfirmDialogClose={this.handleDialogClose}
              onYesClick={this.handleDeleteAll}
              text={t("general.deleteConfirm")}
              agree={t("general.agree")}
              cancel={t("general.cancel")}
            />
          )}
           <Grid item md={4} sm={12} xs={12}>
            <ValidatorForm>
              <AsynchronousAutocomplete
                label={t("Chọn kho")}
                searchFunction={searchStore}
                searchObject={SearchObject}
                defaultValue={kho}
                displayLable={"tenKho"}
                value={kho}
                onSelect={this.selectKho}
              />
            </ValidatorForm>
          </Grid>
          <Grid item xs={12}>
            <div>
              {/* {shouldOpenEditorDialog && (
                <PhieuNhapKhoDialog
                  t={t}
                  i18n={i18n}
                  handleClose={this.handleDialogClose}
                  open={shouldOpenEditorDialog}
                  handleOKEditClose={this.handleOKEditClose}
                  item={item}
                />
              )} */}

              {shouldOpenConfirmationDialog && (
                <ConfirmationDialog
                  title={t("general.confirm")}
                  open={shouldOpenConfirmationDialog}
                  onConfirmDialogClose={this.handleDialogClose}
                  onYesClick={this.handleConfirmationResponse}
                  text={t("general.deleteConfirm")}
                  agree={t("general.agree")}
                  cancel={t("general.cancel")}
                />
              )}
            </div>
            <MaterialTable
              title={t("general.list")}
              data={itemList}
              columns={columns}
              //parentChildData={(row, rows) => rows.find(a => a.id === row.parentId)}
              // parentChildData={(row, rows) => {
              //   var list = rows.find((a) => a.id === row.parentId);
              //   return list;
              // }}
              localization={{
                body: {
                  emptyDataSourceMessage: `${t(
                    "general.emptyDataMessageTable"
                  )}`,
                },
                toolbar: {
                  // nRowsSelected: '${t('general.selects')}',
                  nRowsSelected: `${t("general.selects")}`,
                },
              }}
              options={{
                selection: false,
                actionsColumnIndex: -1,
                paging: false,
                search: false,
                rowStyle: (rowData) => ({
                  backgroundColor:
                    rowData.tableData.id % 2 === 1 ? "#EEE" : "#FFF",
                }),
                maxBodyHeight: "450px",
                minBodyHeight: "370px",
                headerStyle: {
                  backgroundColor: "#358600",
                  color: "#fff",
                },
                padding: "dense",
                toolbar: false,
              }}
              components={{
                Toolbar: (props) => <MTableToolbar {...props} />,
              }}
              onSelectionChange={(rows) => {
                this.data = rows;
                // this.setState({selectedItems:rows});
              }}
            />
            <TablePagination
              align="left"
              className="px-16"
              rowsPerPageOptions={[1, 2, 3, 5, 10, 25, 50, 100]}
              component="div"
              labelRowsPerPage={t("general.rows_per_page")}
              labelDisplayedRows={({ from, to, count }) =>
                `${from}-${to} ${t("general.of")} ${
                  count !== -1 ? count : `more than ${to}`
                }`
              }
              count={totalElements}
              rowsPerPage={rowsPerPage}
              page={page}
              backIconButtonProps={{
                "aria-label": "Previous Page",
              }}
              nextIconButtonProps={{
                "aria-label": "Next Page",
              }}
              onChangePage={this.handleChangePage}
              onChangeRowsPerPage={this.setRowsPerPage}
            />
          </Grid>
        </Grid>
      </div>
    );
  }
}

export default BaoCaoXuat;
