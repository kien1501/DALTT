import React, { Component } from "react";
import {
  IconButton,
  Grid,
  Icon,
  TablePagination,
  Button,
  TextField,
  Tooltip
} from "@material-ui/core";
import MaterialTable, { MTableToolbar } from "material-table";
import { ValidatorForm } from "react-material-ui-form-validator";
import { searchByPage as categoriesSearchByPage ,deleteItem} from "../Slideshow/SlideshowService";
import AsynchronousAutocomplete from "../utilities/AsynchronousAutocomplete";
import {
  searchByPage,
  getItemById,
  changeIsShowOffer,
  addProductToListAgency,
} from "./SlideshowService";
import SlideshowDialog from "./SlideshowDialog";
import { Breadcrumb, ConfirmationDialog, ShowDialog } from "egret";
import { useTranslation } from "react-i18next";
import { Helmet } from "react-helmet";
import { toast } from "react-toastify";
import localStorageService from "../../../app/services/localStorageService";
// import { roleSystem } from "../../role";

toast.configure({
  autoClose: 2000,
  draggable: false,
  limit: 1,
  //etc you get the idea
});

function MaterialButton(props) {
  const { t } = useTranslation();
  const item = props.item;

  return (
    <div>
      <Tooltip title="Delete">
        <IconButton onClick={() => props.onSelect(item, 0)}>
          <Icon color="error">delete</Icon>
        </IconButton>
      </Tooltip>
    </div>
  );
}

class Product extends Component {
  state = {
    rowsPerPage: 25,
    page: 0,
    categories: [],
    locations: [],
    conversionTypes: [],
    item: {},
    shouldOpenEditorDialog: false,
    shouldOpenConfirmationDialog: false,
    selectAllItem: false,
    selectedList: [],
    totalElements: 0,
    shouldOpenConfirmationDeleteAllDialog: false,
    shouldOpenShowDesDialog: false,
    shouldOpenListAgencyToAddProduct: false,
    keyword: "",
    location: [],
    conversionType: [],
  };
  numSelected = 0;
  rowCount = 0;

  setPage = (page) => {
    this.setState({ page }, function () {
      this.updatePageData();
    });
  };

  handleTextChange = (event) => {
    this.setState({ keyword: event.target.value }, function () { });
  };

  handleKeyPress = (event) => {
    if (event.key === "Enter") {
      this.search();
    }
  };

  setRowsPerPage = (event) => {
    this.setState({ rowsPerPage: event.target.value, page: 0 }, function () {
      this.updatePageData();
    });
  };

  handleChangePage = (event, newPage) => {
    this.setPage(newPage);
  };

  selectCategories = (categoriesSelected) => {
    this.setState({ categories: categoriesSelected }, function () {
      this.search();
    });
  };

  selectLocations = (locationsSelected) => {
    this.setState({ locations: locationsSelected }, function () {
      this.search();
    });
  };

  selectConversionTypes = (conversionTypeSelected) => {
    this.setState({ conversionTypes: conversionTypeSelected }, function () {
      this.search();
    });
  };

  search() {
    this.setState({ page: 0 }, function () {
      this.updatePageData();
    });
  }

  handleConfirmationResponse = () => {
    const idList = this.state.selectedItems.map((item) => item.id);
    addProductToListAgency(idList).then(() => {
      this.handleDialogClose();
      this.updatePageData();
    });
  };

  updatePageData = () => {
    var searchObject = {};

    let user = localStorageService.getItem("auth_user");
    // if (user.role === roleSystem.ROLE_PRODUCT) {
    //   searchObject.userId = user.id;
    // }

    searchObject.keyword = this.state.keyword;
    searchObject.categories = this.state.categories.map(
      (category) => category.name
    );
    searchObject.locations = this.state.locations;
    searchObject.conversionTypes = this.state.conversionTypes;
    searchObject.pageIndex = this.state.page + 1;
    searchObject.pageSize = this.state.rowsPerPage;
    searchByPage(searchObject).then(({ data }) => {
      this.setState({
        itemList: [...data.content],
        totalElements: data.totalElements,
      });
    });
  };

  componentDidMount() {
    if (
      this.props.history.location.state &&
      this.props.history.conversionType.state
    ) {
      this.updatePageData();
    }
  }

  componentWillMount() {
  }
  componentDidUpdate(prevProps) {
    if (
      this.props.location.pathname !== prevProps.location.pathname &&
      this.props.conversionType.pathname !== prevProps.conversionType.pathname
    ) {
      this.updatePageData();
    }
  }

  handleDialogClose = () => {
    this.setState({
      shouldOpenEditorDialog: false,
      shouldOpenConfirmationDialog: false,
      shouldOpenConfirmationDeleteAllDialog: false,
      shouldOpenShowDesDialog: false,
      shouldOpenListAgencyToAddProduct: false,
      shouldOpenCloneProduct: false,
    });
    this.updatePageData();
  };

  handleOKEditClose = () => {
    this.setState({
      shouldOpenEditorDialog: false,
      shouldOpenConfirmationDialog: false,
      shouldOpenListAgencyToAddProduct: false,
      shouldOpenCloneProduct: false,
    });
    this.updatePageData();
  };

  handleDeleteItem = (id) => {
    this.setState({
      id,
      shouldOpenConfirmationDialog: true,
    });
  };

  handleEditItem = (item) => {
    getItemById(item.id).then((result) => {
      this.setState({
        item: result.data,
        shouldOpenEditorDialog: true,
      });
    });
  };

  handleConfirmationShowResponse = () => {
    this.setState({ loading: true });
    if (this.state.itemList.length === 1) {
      let count = this.state.page - 1;
      this.setState({
        page: count,
      });
    } else if (this.state.itemList.length === 1 && this.state.page === 1) {
      this.setState({
        page: 1,
      });
    }

    changeIsShowOffer(this.state.id).then(() => {
      this.setState({ loading: false });
      this.updatePageData();
      this.handleDialogClose();
    });
  };

  handleHide = (isShow, id) => {
    this.setState({
      id,
      isShow,
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

  handleDelete = (id) => {
    this.setState({
      id,
      shouldOpenConfirmationDialog: true,
    });
  };

  render() {
    const { t, i18n } = this.props;
    let searchObject = { pageIndex: 0, pageSize: 1000000 };
    let {
      categories,
      keyword,
      rowsPerPage,
      page,
      totalElements,
      itemList,
      item,
      loading,
      shouldOpenConfirmationDialog,
      shouldOpenEditorDialog,
      shouldOpenConfirmationDeleteAllDialog,
      shouldOpenShowDesDialog,
      shouldOpenCloneProduct,
    } = this.state;
    let TitlePage = t("Danh sách ảnh");
    let columns = [
      {
        title: t("general.action"),
        field: "custom",
        align: "left",

        headerStyle: {
          paddingLeft: "3px",
        },
        cellStyle: {
          paddingLeft: "3px",
        },
        render: (rowData) => (
          <MaterialButton
            item={rowData}
            onSelect={(rowData, method) => {
              if (method === 0) {
                deleteItem(rowData.id).then(({ data }) => {
                  toast.success("Xóa thành công")
                  this.updatePageData();
                });
              } else if (method === 1) {
                this.handleHide(rowData.isShow, rowData.id);
              } else if (method === 2) {
                this.setState({
                  detail: (
                    <>
                      <h4>{t("Product.createdBy")}</h4>
                      <p>{rowData.createdBy}</p>
                      <h4>{t("Agency.description")}</h4>
                      <p>{rowData.description}</p>
                    </>
                  ),
                  shouldOpenShowDesDialog: true,
                });
              } else if (method === 3) {
                this.setState({
                  productId: rowData.id,
                  shouldOpenListAgencyToAddProduct: true,
                });
              } else if (method === 4) {
                getItemById(rowData.id).then(({ data }) => {
                  let productInfo = data;

                  this.setState({
                    item: productInfo,
                    shouldOpenCloneProduct: true,
                  });
                });
              } else {
                alert("Call Selected Here:" + rowData.id);
              }
            }}
          />
        ),
      },
      {
        title: t("Đường dẫn"),
        field: "url",
        width: "80%",
        align: "left",
        render: (rowData) =>
          <img src={rowData.url} style={{width:200, height:100}}/> 
      },
    ];

    return (
      <div className="m-sm-30">
        <Helmet>
          <title> {TitlePage} | {t("web_site")}</title>
        </Helmet>
        <div className="mb-sm-30">
          <Breadcrumb routeSegments={[{ name: t("Danh sách ảnh") }]} />
        </div>

        <Grid container spacing={1} xs={12}>
          <Grid item md={4} sm={4} xs={4}>
            <Button
              className=" mr-16 align-bottom mb-10"
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
            {/* <TextField
              label={t("Tìm kiếm")}
              className=" mr-10 mb-10"
              style={{ width: 250 }}
              fontSize="small"
              type="text"
              name="keyword"
              value={keyword}
              onChange={this.handleTextChange}
              onKeyPress={this.handleKeyPress}
            />
            <Button
              className=" align-bottom mb-10"
              variant="contained"
              color="primary"
              onClick={() => this.search(keyword)}
              type="submit"
            >
              <Icon fontSize="default">search</Icon>
            </Button>
          </Grid>
          <Grid item md={4} sm={4} xs={4}></Grid>*/}
          {/* <Grid item md={12} sm={12} xs={4}>
            <ValidatorForm ref="form" onSubmit={this.handleFormSubmit}>
              <AsynchronousAutocomplete
                label={t("Chọn")}
                searchFunction={categoriesSearchByPage}
                className=" mr-10 mb-10"
                multiple={true}
                searchObject={searchObject}
                defaultValue={categories}
                displayLable={"name"}
                value={categories}
                onSelect={this.selectCategories}
              />
            </ValidatorForm>
            </Grid>  */}
          </Grid> 

          <Grid item xs={12}>
            <div>
              {shouldOpenConfirmationDeleteAllDialog && (
                <ConfirmationDialog
                  open={shouldOpenConfirmationDeleteAllDialog}
                  onConfirmDialogClose={this.handleDialogClose}
                  onYesClick={this.handleDeleteAll}
                  text={t("general.deleteAllConfirm")}
                />
              )}
              {shouldOpenEditorDialog && (
                <SlideshowDialog
                  t={t}
                  i18n={i18n}
                  handleClose={this.handleDialogClose}
                  open={shouldOpenEditorDialog}
                  handleOKEditClose={this.handleOKEditClose}
                  item={item}
                />
              )}
              {shouldOpenCloneProduct && (
                <SlideshowDialog
                  isClone={true}
                  t={t}
                  i18n={i18n}
                  handleClose={this.handleDialogClose}
                  open={shouldOpenCloneProduct}
                  handleOKEditClose={this.handleOKEditClose}
                  item={item}
                />
              )}
              {shouldOpenConfirmationDialog && (
                <ConfirmationDialog
                  title={t("general.confirm")}
                  open={shouldOpenConfirmationDialog}
                  disabled={loading}
                  onConfirmDialogClose={this.handleDialogClose}
                  onYesClick={this.handleConfirmationShowResponse}
                  text={
                    this.state.isShow
                      ? t("general.hide_product_confirm")
                      : t("general.show_product_confirm")
                  }
                  agree={t("general.agree")}
                  cancel={t("general.cancel")}
                />
              )}

              {shouldOpenShowDesDialog && (
                <ShowDialog
                  title={""}
                  open={shouldOpenShowDesDialog}
                  onConfirmDialogClose={this.handleDialogClose}
                  text={this.state.detail}
                  cancel={"Cancel"}
                />
              )}
            </div>
            <MaterialTable
              data={itemList}
              columns={columns}
              localization={{
                body: {
                  emptyDataSourceMessage: `${t(
                    "general.emptyDataMessageTable"
                  )}`,
                },
                toolbar: {
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
                    rowData.tableData.id % 2 == 1 ? "#EEE" : "#FFF",
                  backgroundColor:
                    rowData.isShow == false
                      ? "#b59edb"
                      : rowData.tableData.id % 2 === 0
                        ? "#ffffff"
                        : "#eeeeee",
                }),
                maxBodyHeight: "450px",
                minBodyHeight: "370px",
                headerStyle: {
                  backgroundColor: "#358600",
                  color: "#fff",
                },
                cellStyle: {
                  whiteSpace: "normal",
                  overflow: "hidden",
                  textOverflow: "ellipsis",
                  overflowWrap: "break-word",
                },
                padding: "dense",
                toolbar: false,
              }}
              components={{
                Toolbar: (props) => <MTableToolbar {...props} />,
              }}
              onSelectionChange={(rows) => {
                this.data = rows;
              }}
            />
            <TablePagination
              align="left"
              className="px-16"
              labelRowsPerPage={t("general.rowperpage")}
              rowsPerPageOptions={[5, 10, 25, 50]}
              component="div"
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

export default Product;
