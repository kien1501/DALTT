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

} from "@material-ui/core";
// import Paper from '@material-ui/core/Paper'
import { ValidatorForm, TextValidator } from "react-material-ui-form-validator";
import Draggable from "react-draggable";
import NotificationPopup from "../Component/NotificationPopup/NotificationPopup";
import ChonKho from "./ChonKhoNhap";
import ChonNhanVien from "./ChonNhanVien";
import {
    saveItem,
  addItem,
  updateItem,
  checkCode,
} from "./PhieuXuatKhoService";
import MaterialTable, {
  MTableToolbar,
  Chip,
  MTableBody,
  MTableHeader,
} from "material-table";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import MultipleProduct from "./MultipleProduct";
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
class PhieuXuatKhoDialog extends Component {
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
  handleSelectUser =(item)=>{
      this.setState({warehouse:item ? item : null,shouldOpenChonKho: false, })
  }
  handleSelectAgency =(item) =>{
    this.setState({exporter:item ? item : null,shouldOpenChonNhanVien: false, })
  }
  handleChangeSL  =(item,e) =>{
    let {productInventoryDeliveryVoucher} = this.state
    if(productInventoryDeliveryVoucher == null ){
      productInventoryDeliveryVoucher =[] 
      let p = {}
      p = item;
      p.productNumber = e.target.value
      productInventoryDeliveryVoucher.push(p)
    }
    if(productInventoryDeliveryVoucher != null && productInventoryDeliveryVoucher.length > 0){
      productInventoryDeliveryVoucher.forEach(el=>{
        if(el.productColor.id == item.productColor.id){
          // let p ={}
          el.productNumber =e.target.value
        }
      })
    }
    this.setState({productInventoryDeliveryVoucher:productInventoryDeliveryVoucher},()=>{
      console.log(this.state.productInventoryDeliveryVoucher)
    })
  }
  //
  // handleChangeGia  =(item,e) =>{
  //   let {sanPhamPhieuXuat} = this.state
  //   if(sanPhamPhieuXuat == null ){
  //     sanPhamPhieuXuat =[] 
  //     let p = {}
  //     p.sanPham = item.sanPham;
  //     p.gia = e.target.value
  //     productInventoryDeliveryVoucher.push(p)
  //   }
  //   if(productInventoryDeliveryVoucher != null && productInventoryDeliveryVoucher.length > 0){
  //     productInventoryDeliveryVoucher.forEach(el=>{
  //       if(el.product.id == item.product.id){
  //         // let p ={}
  //         el.gia =e.target.value
  //       }
  //     })
  //   }
  //   this.setState({productInventoryDeliveryVoucher:productInventoryDeliveryVoucher},()=>{
  //     console.log(this.state.productInventoryDeliveryVoucher)
  //   })
  // }
  handleSelectSP = (item) => {
    console.log(item)
    let data = item.map((row) => ({ ...row, tableData: { checked: false } }));
    this.setState({ productInventoryDeliveryVoucher: data },()=>{
      console.log(this.state.productInventoryDeliveryVoucher)
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
      productInventoryDeliveryVoucher,
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
                productInventoryDeliveryVoucher.map((pro, index) => {
                  if (pro.productColor.id === rowData.productColor.id) {
                    productInventoryDeliveryVoucher.splice(index, 1);
                  }
                });
                this.setState({ productInventoryDeliveryVoucher: productInventoryDeliveryVoucher });
              } else {
                alert("Call Selected Here:" + rowData.id);
              }
            }}
          />
        ),
      },
      {
        title: t("Tên sản phẩm"),
        field: "productColor.product.name",
        width: "300",
      },
      {
        title: t("Mã sản phẩm"),
        field: "productColor.product.code",
        width: "300",
      },
      {
        title: t("Mã sản phẩm"),
        field: "productColor.color.name",
        width: "300",
      },
      {
        title: t("Số lượng"),
        field: "productNumber",
        align: "left",
        render: (row) => 
              <TextValidator
                  className="w-100 "
                  onChange={(e) =>this.handleChangeSL(row,e)}
                  type="number"
                  value={row.productNumber}
                  validators={["required"]}
                  errorMessages={[t("Chưa nhập số lượng")]}
                />
        
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
              <Grid item sm={12} xs={12}>
                <TextValidator
                  className="w-100 "
                  label={
                    <span>
                      <span style={{ color: "red" }}>*</span>
                      {t("general.name")}
                    </span>
                  }
                  onChange={this.handleChange}
                  type="text"
                  name="name"
                  value={name}
                  validators={["required"]}
                  errorMessages={[t("general.required")]}
                  variant="outlined"
                  size ="small"
                />
              </Grid>

              <Grid item sm={12} xs={12}>
                <TextValidator
                  className="w-100 "
                  label={
                    <span>
                      <span style={{ color: "red" }}>*</span>
                      {t("general.code")}
                    </span>
                  }
                  onChange={this.handleChange}
                  type="text"
                  name="code"
                  value={code}
                  validators={["required"]}
                  errorMessages={[t("general.required")]}
                  variant="outlined"
                  size ="small"
                />
              </Grid>
              <Grid item  md={12} sm={12} xs={12}>
                <MuiPickersUtilsProvider utils={DateFnsUtils}>
                  <KeyboardDatePicker
                    fullWidth
                    margin="none"
                    id="mui-pickers-date"
                    label={<span className= "font">{t('Ngày xuất kho')}</span>}
                    inputVariant="outlined"
                    size="small"
                    type="text"
                    autoOk={false}
                    format="dd/MM/yyyy"
                    value={this.state.exportDate}
                    onChange={date => this.handleDateChange(date, "exportDate")}
                  />
                </MuiPickersUtilsProvider>
              </Grid>
              <Grid item sm={12} xs={12}>
                <Button
                  size="small"
                  style={{ float: "right" }}
                  className=" mt-10"
                  variant="contained"
                  color="primary"
                  onClick={()=>{
                      this.setState({shouldOpenChonNhanVien:true})
                  }}
                >
                  {t("general.select")}
                </Button>
                <TextValidator
                  size="small"
                  InputLabelProps={{ shrink: true }}
                  InputProps={{
                    readOnly: true,
                  }}
                  label={
                    <span>
                      <span style={{ color: "red" }}></span>
                      {t("Người nhập")}
                    </span>
                  }
                  // className="w-80"
                  style ={{width: "90%"}}
                  value={
                    this.state.exporter != null ? this.state.exporter.displayName : ""
                  }
                  variant="outlined"
                  size ="small"
                />

                {this.state.shouldOpenChonNhanVien && (
                  <ChonNhanVien
                    open={this.state.shouldOpenChonNhanVien}
                    handleSelect={this.handleSelectAgency}
                    selectedItem={
                      this.state.exporter != null
                        ? this.state.exporter
                        : {}
                    }
                    handleClose={this.handleDialogClose}
                    t={t}
                    i18n={i18n}
                  />
                )}
              </Grid>
              <Grid item sm={12} xs={12}>
                <Button
                  size="small"
                  style={{ float: "right" }}
                  className=" mt-10"
                  variant="contained"
                  color="primary"
                  onClick={()=>{
                      this.setState({shouldOpenChonKho:true})
                  }}
                >
                  {t("general.select")}
                </Button>
                <TextValidator
                  size="small"
                  InputLabelProps={{ shrink: true }}
                  InputProps={{
                    readOnly: true,
                  }}
                  label={
                    <span>
                      <span style={{ color: "red" }}></span>
                      {t("Kho")}
                    </span>
                  }
                  // className="w-80"
                  style ={{width: "90%"}}
                  value={
                    this.state.warehouse != null ? this.state.warehouse.name : ""
                  }
                  variant="outlined"
                  size ="small"
                />

                {this.state.shouldOpenChonKho && (
                  <ChonKho
                    open={this.state.shouldOpenChonKho}
                    handleSelect={this.handleSelectUser}
                    selectedItem={
                      this.state.warehouse != null 
                        ? this.state.warehouse
                        : {}
                    }
                    handleClose={this.handleDialogClose}
                    t={t}
                    i18n={i18n}
                  />
                )}
                <Button
                    className=" mt-10 mb-10"
                    variant="contained"
                    color="primary"
                    onClick={() =>{
                      if(this.state.warehouse  == null){
                        toast.warning(t("Chưa chọn kho"));
                        return
                      }
                      this.setState({
                        shouldOpenMultipleDialog: true,
                        item: {},
                      })
                    }
                      
                    }
                  >{t("general.select")}</Button>
                {this.state.shouldOpenMultipleDialog && (
                <MultipleProduct
                  open={this.state.shouldOpenMultipleDialog}
                  selected={this.state.productInventoryDeliveryVoucher}
                  handleSelect={this.handleSelectSP}
                  handleClose={this.handleDialogCancel}
                  t={t}
                  i18n={i18n}
                  khoId={this.state.warehouse ? this.state.warehouse.id:""}
                />
              )}
                <Grid item sm={12} xs="12" className = "mt-10">
                  <MaterialTable
                    data={this.state.productInventoryDeliveryVoucher ? this.state.productInventoryDeliveryVoucher : []}
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

export default PhieuXuatKhoDialog;
