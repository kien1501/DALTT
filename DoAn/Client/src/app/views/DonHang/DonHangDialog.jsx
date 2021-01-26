import React, { Component } from "react";
import {
  Dialog,
  Button,
  Grid,
  DialogActions,
  FormControl,
  Paper,
  DialogTitle,
  DialogContent,
  Icon,
  IconButton,
  InputLabel,
  Select,
  MenuItem

} from "@material-ui/core";
// import Paper from '@material-ui/core/Paper'
import { ValidatorForm, TextValidator } from "react-material-ui-form-validator";
import Draggable from "react-draggable";
import NotificationPopup from "../Component/NotificationPopup/NotificationPopup";
import {
    saveItem,
  addItem,
  updateItem,
  checkCode,
} from "./DonHangService";
import MaterialTable, {
  MTableToolbar,
  Chip,
  MTableBody,
  MTableHeader,
} from "material-table";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { makeStyles, withStyles } from "@material-ui/core/styles";
import Tooltip from "@material-ui/core/Tooltip";
import { useTranslation, withTranslation, Trans } from "react-i18next";
import { MuiPickersUtilsProvider, DateTimePicker,KeyboardDatePicker } from "@material-ui/pickers";
import DateFnsUtils from "@date-io/date-fns";
toast.configure({
  autoClose: 2000,
  draggable: false,
  limit: 3,
});

function PaperComponent(props) {
  return (
    <Draggable
      handle="#draggable-dialog-title"
      cancel={'[class*="MuiDialogContent-root"]'}
    >
      <Paper {...props} />
    </Draggable>
  );
}
const LightTooltip = withStyles((theme) => ({
  tooltip: {
    backgroundColor: theme.palette.common.white,
    color: "rgba(0, 0, 0, 0.87)",
    boxShadow: theme.shadows[1],
    fontSize: 11,
    position: "absolute",
    top: "-10px",
    left: "-25px",
    width: "80px",
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
class DonHangDialog extends Component {
  state = {
    id: "",
    name: "",
    code: "",
    description: "",
    type: "",
    shouldOpenNotificationPopup: false,
    Notification: "",
    shouldOpenChonKho: false,
    ChonNhanVien: false,
    agency:null,
    user:null,
    shouldOpenMultipleDialog : false,
  };
  listType = [
    { id: 1, name: 'Xác nhận' },
    { id: 2, name: 'Chờ chuyển' },
    { id: 3, name: 'Đã chuyển' }
  ]
  handleDialogClose = () => {
    this.setState({ shouldOpenNotificationPopup: false,
                    shouldOpenChonKho:false,
                    shouldOpenChonNhanVien: false,
                    shouldOpenMultipleDialog : false,
                 });
  };
  handleDialogCancel = () => {
    this.setState({
      shouldOpenMultipleDialog : false,
    });
  };
  handleChange = (event, source) => {
    event.persist();
    if (source === "switch") {
      this.setState({ isActive: event.target.checked });
      return;
    }
    if (source === "status") {
      this.setState({ status: event.target.value });
      return;
    }
    this.setState({
      [event.target.name]: event.target.value,
    });
  };

  handleFormSubmit = () => {
    let { id } = this.state;
    let { code } = this.state;
    var { t } = this.props;
    checkCode(id, code).then((result) => {
      //Nếu trả về true là code đã được sử dụng
      if (result.data) {
        toast.warning(t("general.dupli_code"));
        // alert("Code đã được sử dụng");
      } else {
        //Nếu trả về false là code chưa sử dụng có thể dùng
        if (id) {
          updateItem({
            ...this.state,
          }).then(() => {
            toast.success(t("general.updateSuccess"));
            this.props.handleOKEditClose();
          });
        } else {
            saveItem({
            ...this.state,
          }).then(() => {
            toast.success(t("general.addSuccess"));
            this.props.handleOKEditClose();
          });
        }
      }
    });
  };

  componentWillMount() {
    //getUserById(this.props.uid).then(data => this.setState({ ...data.data }));
    let { open, handleClose, item } = this.props;
    this.setState({...item});
  }
  
  
  
  handleSelectSP = (item) => {
    let data = item.map((row) => ({ ...row, tableData: { checked: false } }));
    this.setState({ productInventoryReceivingVoucher: data },()=>{
      console.log(this.state.productInventoryReceivingVoucher)
    });
    this.handleDialogCancel();
  };
  handleDateChange = (date, name) => {
    this.setState({
      [name]: date
    });
  };
  render() {
    let {
      id,
      code,
      name,
      description,
      productOrder,
    } = this.state;
    
    let { open, handleClose, handleOKEditClose, t, i18n } = this.props;

    let columns = [
      {
        title: t("general.action"),
        field: "custom",
        align: "left",
        width: "90px",
        headerStyle: {
          padding: "0px",
        },
        cellStyle: {
          padding: "0px",
        },
        render: (rowData) => (
          <MaterialButton
            item={rowData}
            onSelect={(rowData, method) => {
              if (method === 0) {
                this.setState({shouldOpenLabTestPropertyEditDialog: true, item: rowData});
              } else if (method === 1) {
                productOrder.map((pro, index) => {
                  if (pro.product.code === rowData.product.code) {
                    productOrder.splice(index, 1);
                  }
                });
                this.setState({ productOrder: productOrder });
              } else {
                alert("Call Selected Here:" + rowData.id);
              }
            }}
          />
        ),
      },
      {
        title: t("Tên sản phẩm"),
        field: "product.name",
        width: "300",
      },
      {
        title: t("Mã sản phẩm"),
        field: "product.code",
        width: "300",
      },
      {
        title: t("Số lượng"),
        field: "productNumber",
        width: "300",
      },
      {
        title: t("Số lượng"),
        field: "productNumber",
        width: "300",
      },
      
    ];
    return (
      <Dialog
        open={open}
        PaperComponent={PaperComponent}
        maxWidth="md"
        fullWidth
      >
        <DialogTitle
          style={{ cursor: "move", paddingBottom: "0px" }}
          id="draggable-dialog-title"
        >
          <h4 className="">{id ? t("general.update") : t("general.addNew")}</h4>
        </DialogTitle>

        <ValidatorForm ref="form" onSubmit={this.handleFormSubmit}>
          <DialogContent>
            <Grid className="" container spacing={2} >
            <Grid item md={12} sm={12} xs={12} className="mt-10">
                <FormControl fullWidth={true} variant="outlined"
                  size="small">
                  <InputLabel htmlFor="gender-simple">{<span className="font"><span style={{ color: "red" }}>*</span>Trạng thái đơn hàng</span>}</InputLabel>
                  <Select
                    value={this.state.status}
                    onChange={status => this.handleChange(status, "status")}
                    inputProps={{
                      name: "status",
                      id: "gender-simple"
                    }}
                  >
                    {this.listType.map(item => {
                      return <MenuItem key={item.id} value={item.id}>{item.name}</MenuItem>;
                    })}
                  </Select>
                </FormControl>
              </Grid>
                <Grid item sm={12} xs="12" className = "mt-10">
                  <MaterialTable
                    data={this.state.productOrder ? this.state.productOrder : []}
                    columns={columns}
                    options={{
                      selection: false,
                      actionsColumnIndex: 0,
                      paging: false,
                      search: false,
                      rowStyle: (rowData) => ({
                        backgroundColor:
                          rowData.tableData.id % 2 === 1 ? "#EEE" : "#FFF",
                      }),
                      maxBodyHeight: "253px",
                      minBodyHeight: "253px",
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
                    }}
                  />
                </Grid>
              </Grid>
          </DialogContent>
          <DialogActions>
            <div className="flex flex-space-between flex-middle mt-12">
              <Button
                variant="contained"
                className="mr-12"
                color="secondary"
                onClick={() => this.props.handleClose()}
              >
                {t("general.cancel")}
              </Button>
              <Button
                variant="contained"
                style={{ marginRight: "15px" }}
                color="primary"
                type="submit"
              >
                {t("general.save")}
              </Button>
            </div>
          </DialogActions>
        </ValidatorForm>
      </Dialog>
    );
  }
}

export default DonHangDialog;
