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
  InputLabel,
  Select,
  MenuItem
} from "@material-ui/core";
// import Paper from '@material-ui/core/Paper'
import { ValidatorForm, TextValidator } from "react-material-ui-form-validator";
import Draggable from "react-draggable";
import NotificationPopup from "../Component/NotificationPopup/NotificationPopup";
import {
  addNewUrbanArea,
  updateUrbanArea,
  checkCode,
} from "./NhanVienService";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { pageItem } from "../ShiftWork/ShiftWorkService";
import TextField from '@material-ui/core/TextField';
import Autocomplete from '@material-ui/lab/Autocomplete';

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

class UrbanAreaDialog extends Component {
  constructor(props) {
    super(props);
    pageItem(1, 1000).then((data) => {
      let listShiftWork = data.data.content;
      this.setState({ listShiftWork: listShiftWork });
      console.log(this.state.listShiftWork);
    });
  }
  state = {
    id: "",
    code: "",
    type: "",
    displayName: "",
    type: "",
    email: "",
    phoneNumber: "",
    shouldOpenNotificationPopup: false,
    Notification: "",
    shiftWork: "",
    listShiftWork: [],
    isTruePhoneNumer: false,
    isTrueEmail: false,
    isEdit:true
  };
  listType = [
    { id: 1, name: 'Nhân viên bán hàng' },
    { id: 2, name: 'Nhân viên thu ngân' },
    { id: 3, name: 'Khác' }
  ]
  handleDialogClose = () => {
    this.setState({ shouldOpenNotificationPopup: false });
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
    if (source === "type") {
      let { type } = this.state;
      type = event.target.value;
      this.setState({ type: type });
      return;
    }
  };
  handleSelectShiftWork = (shiftWork) => {
    this.setState({ shiftWork: shiftWork }, function () {
    });
  }

  handleFormSubmit = () => {
    debugger
    let { id } = this.state;
    let { code } = this.state;
    var { t } = this.props;
    checkCode(id, code).then((result) => {
      if (result.data) {
        toast.warning("Mã nhân viên đã sử dụng, vui lòng điền một mã khác.");
      } else {
        if (id) {
          updateUrbanArea({
            ...this.state,
          }).then((res) => {
            // if(res.data.hasPhoneNumber == true || res.data.hasUserName == true){
            //   toast.warning("Số điện thoại này đã được sử dụng");
            //   return;
            // }
            // if(res.data.hasEmail == true){
            //   toast.warning("Email này đã được sử dụng");
            //   return;
            // }
            toast.success("Cập nhật thành công thông tin nhân viên");
            this.props.handleOKEditClose();
          });
        } else {
          addNewUrbanArea({
            ...this.state,
          }).then((res) => {
            // if(res.data.hasPhoneNumber == true || res.data.hasUserName == true){
            //   toast.warning("Số điện thoại này đã được sử dụng");
            //   return;
            // }
            // if(res.data.hasEmail == true){
            //   toast.warning("Email này đã được sử dụng");
            //   return;
            // }
            toast.success("Thêm mới thành công nhân viên");
            this.props.handleOKEditClose();
          });
        }
      }
    });
  };


  componentWillMount() {
    let { open, handleClose, item,isEdit } = this.props;
    this.setState(item,isEdit);
  }
  componentDidMount() {
    debugger
    ValidatorForm.addValidationRule('isTruePhoneNumer', (value) => {
      if (this.isVietnamesePhoneNumber(value)) {
        return true;
      }
      return false;
    });
    ValidatorForm.addValidationRule('isTrueEmail', (value) => {
      if (this.isTestEmail(value)) {
        return true;
      }
      return false;
    });
  }
  isVietnamesePhoneNumber = (number) => {
    return /(03|05|07|08|09|01[2|6|8|9])+([0-9]{8})\b/.test(number);
  }
  isTestEmail = (email) => {
    var pattern = new RegExp(/^(("[\w-\s]+")|([\w-]+(?:\.[\w-]+)*)|("[\w-\s]+")([\w-]+(?:\.[\w-]+)*))(@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$)|(@\[?((25[0-5]\.|2[0-4][0-9]\.|1[0-9]{2}\.|[0-9]{1,2}\.))((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\.){2}(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\]?$)/i);
    if (pattern.test(email)) {
      return true;
    }
    return false;
  }


  render() {
    let {
      id,
      code,
      type,
      displayName,
      email,
      phoneNumber,
      shiftWork,
      listShiftWork,
      isTruePhoneNumer,
      isTrueEmail,
      isEdit,
      shouldOpenNotificationPopup,
    } = this.state;
    let { open, handleClose, handleOKEditClose, t, i18n } = this.props;
    return (
      <Dialog
        open={open}
        PaperComponent={PaperComponent}
        maxWidth="sm"
        fullWidth
      >
        <DialogTitle
          style={{ cursor: "move", paddingBottom: "0px" }}
          id="draggable-dialog-title"
        >
          <h4 className="">{id ? (<span>Sửa thông tin nhân viên</span>) : (<span>Thêm nhân viên</span>)}</h4>
        </DialogTitle>
        <ValidatorForm ref="form" onSubmit={this.handleFormSubmit}>
          <DialogContent>
            <Grid className="" container spacing={2}>
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
                />
              </Grid>
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
                  name="displayName"
                  value={displayName}
                  validators={["required"]}
                  errorMessages={[t("general.required")]}
                />
              </Grid>
              <Grid item sm={12} xs={12}>
                <TextValidator
                  className="w-100 "
                  label={
                    <span>
                      <span style={{ color: "red" }}>*</span>
                      Điện thoại liên hệ
                    </span>
                  }
                  disabled={isEdit}
                  onChange={phoneNumber => this.handleChange(phoneNumber, "phoneNumber")}
                  type={isTruePhoneNumer ? "phoneNumber" : "number"}
                  name="phoneNumber"
                  value={phoneNumber}
                  validators={['required', 'isTruePhoneNumer']}
                  errorMessages={[t("general.errorMessages_required"), t("general.isTruePhoneNumer")]}
                />
              </Grid>
              <Grid item sm={12} xs={12}>
                <TextValidator
                  className="w-100 "
                  label={
                    <span>
                      <span style={{ color: "red" }}>*</span>
                      Email
                    </span>
                  }
                  onChange={this.handleChange}
                  type={isTrueEmail ? "email" : "email"}
                  name="email"
                  value={email}
                  validators={['required', 'isTrueEmail']}
                  errorMessages={[t("general.errorMessages_required"), t("general.isTrueEmail")]}
                />
              </Grid>
              <Grid item md={12} sm={12} xs={12} className="mt-10">
              
                <FormControl fullWidth={true} variant="outlined"
                  size="small">
                  <InputLabel htmlFor="gender-simple">{<span className=""><span style={{ color: "red" }}>*</span>Loại nhân viên</span>}</InputLabel>
                  <Select
                   label = {<span className={""}><span style={{color:"red"}}>*</span>Loại nhân viên</span>}
                    value={type}
                    onChange={type => this.handleChange(type, "type")}
                    inputProps={{
                      name: "gender",
                      id: "gender-simple"
                    }}
                  >
                    {this.listType.map(item => {
                      return <MenuItem key={item.id} value={item.id}>{item.name}</MenuItem>;
                    })}
                  </Select>
                </FormControl>
              </Grid>
              <Grid item md={12} sm={12} xs={12} className="mt-10">
                {listShiftWork && (<Autocomplete
                  variant="outlined"
                  size="small"
                  style={{ width: '100%' }}
                  id="combo-box-demo"
                  defaultValue={shiftWork}
                  value={shiftWork}
                  options={listShiftWork}
                  getOptionSelected={(option, value) => option.id === value.id}
                  getOptionLabel={(option) => option.name}
                  onChange={(event, value) => {
                    this.handleSelectShiftWork(value);
                  }}
                  renderInput={(params) =>
                    <TextValidator
                      {...params}
                      value={shiftWork}
                      label={
                        <span className="font">
                          <span style={{ color: "red" }}> * </span>
                          Ca làm việc
                        </span>
                      }
                      fullWidth
                      validators={["required"]}
                      errorMessages="Vui lòng chọn ca làm việc"
                      variant="outlined"
                      size="small"
                    />}

                />)}
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

export default UrbanAreaDialog;
