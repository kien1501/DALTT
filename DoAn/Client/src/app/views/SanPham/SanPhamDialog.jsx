import axios from "axios";
import React, { Component } from "react";
import ConstantList from "../../appConfig";
import {
  Dialog,
  Button,
  Grid,
  DialogActions,
  FormControl,
  Paper,
  DialogTitle,
  DialogContent,
  Icon, Fab, Card
} from "@material-ui/core";
// import Paper from '@material-ui/core/Paper'
import { ValidatorForm, TextValidator } from "react-material-ui-form-validator";
import Draggable from "react-draggable";
import NotificationPopup from "../Component/NotificationPopup/NotificationPopup";
import SelectUserPopup from "./SelectUserPopup";
import SelectAgencyPopup from "./SelectAgencyPopup";
import Autocomplete from "@material-ui/lab/Autocomplete";
import {
  saveItem,
  addItem,
  updateItem,
  checkCode, uploadImage,
} from "./SanPhamService";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import EditorForm from "./EditorForm";
import { searchByPage as getDM } from '../DanhMucSanPham/DanhMucSanPhamService'
import { searchByPage as getNCC } from '../NhaCungCap/SupplierService'
import UploadImage from "../forms/UploadImage";
import { pageItem } from "../Color/ColorService";

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

class AgentDialog extends Component {
  constructor(props) {
    super(props);
    pageItem(1, 1000).then((data) => {
      let listColors = data.data.content;
      this.setState({ listColors: listColors });
      console.log(this.state.listColors);
    });
  }
  state = {
    id: "",
    name: "",
    code: "",
    description: "",
    type: "",
    shouldOpenNotificationPopup: false,
    Notification: "",
    shouldOpenSelectUserPopup: false,
    SelectAgencyPopup: false,
    agency: null,
    user: null,
    shouldOpenFileBrowserDialog: false,
    imageUrl: "",
    noteAvatarImage: "",
    files: [],
    productColors: [],
    listColors: [],
    mainImageUrl: ""
  };

  handleDialogClose = () => {
    this.setState({
      shouldOpenNotificationPopup: false,
      shouldOpenSelectUserPopup: false,
      shouldOpenSelectAgencyPopup: false,
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
          }).then((res) => {
            if (this.state.file !== null) {
              this.setState(
                {
                  loading: false,
                  shouldOpenDialog: true,
                  productId: res.data.id,
                },
                () => {
                }
              );
              if (this.state.file && this.state.file.length > 0) {
                for (var i = 0; i < this.state.file.length; i++) {
                  console.log("WWWWWWWWWWWWWW)");
                  const url = ConstantList.API_ENPOINT + "/api/upload/image";
                  let formData = new FormData();
                  formData.append('file', this.state.file[i]);
                  formData.append('productID', res.data.id);
                  const config = {
                    headers: {
                      'Content-Type': 'multipart/form-data'
                    }
                  }
                  axios.post(url, formData, config)
                }
              }
            }
            toast.success(t("general.updateSuccess"));
            this.props.handleOKEditClose();
          }).catch((err)=>{
            toast.error("Có lỗi xảy ra khi lưu thông tin sản phẩm");
          })
        } else {
          saveItem({
            ...this.state,
          }).then((res) => {
            if (this.state.file !== null) {
              this.setState(
                {
                  loading: false,
                  shouldOpenDialog: true,
                  productId: res.data.id,
                },
                () => {
                }
              );

              if (this.state.file && this.state.file.length > 0) {
                for (var i = 0; i < this.state.file.length; i++) {
                  console.log("WWWWWWWWWWWWWW)");
                  const url = ConstantList.API_ENPOINT + "/api/upload/image";
                  let formData = new FormData();
                  formData.append('file', this.state.file[i]);
                  formData.append('productID', res.data.id);
                  const config = {
                    headers: {
                      'Content-Type': 'multipart/form-data'
                    }
                  }
                  axios.post(url, formData, config)
                }
              }


            }
            // toast.success(t("general.updateSuccess"));
            // this.props.handleOKEditClose();
            // });
            toast.success(t("general.addSuccess"));
            this.props.handleOKEditClose();
          }).catch((err)=>{
            toast.error("Có lỗi xảy ra khi lưu thông tin sản phẩm");
          })
        }
      }
    });
  };
  handleChangeContent = (content) => {
    this.setState({ posts: content });
  };
  componentWillMount() {
    //getUserById(this.props.uid).then(data => this.setState({ ...data.data }));
    let { open, handleClose, item } = this.props;
    var searchObject = {};
    searchObject.pageIndex = 1;
    searchObject.pageSize = 100000;
    getDM(searchObject).then(res => {
      this.setState({ listDM: [...res.data.content] })
    })
    getNCC(searchObject).then(res => {
      this.setState({ listSupplier: [...res.data.content] })
    })

    this.setState({ ...item });
  }
  handleSelectUser = (item) => {
    this.setState({ user: item ? item : null, shouldOpenSelectUserPopup: false, })
  }
  handleSelectAgency = (item) => {
    this.setState({ agency: item ? item : null, shouldOpenSelectAgencyPopup: false, })
  }
  handleFileBrowserDialogClose = () => {
    this.setState({ shouldOpenFileBrowserDialog: false });
  };

  handleFileSelect = (event) => {
    event.preventDefault();
    let files = event.target.files;
    let file = files[0];
    let list = [];
    console.log(file);
    if (
      file.type !== "image/jpg" &&
      file.type !== "image/jpeg" &&
      file.type !== "image/png"
    ) {
      toast.error("File incorrect format!");
    } else {
      if (file.size >= 7097152) {
        toast.error("File can't be larger than 7mb!");
      } else {
        for (const iterator of files) {
          list.push({
            file: iterator,
            uploading: false,
            error: false,
            progress: 0,
          });
        }
        this.setState(
          {
            files: list,
          },
          () => {
            let file = this.state.files[0];

            const formData = new FormData();
            if (file) {
              formData.append("uploadfile", file.file);
              uploadImage(formData).then(({ data }) => {
                this.setState({ imageUrl: data.name });
              });
            }
          }
        );
      }
    }
  };
  uploadSingleFile = (index) => {
    let allFiles = [...this.state.files];
    let file = this.state.files[index];

    allFiles[index] = { ...file, uploading: true, error: false };

    this.setState({
      files: [...allFiles],
    });
    const formData = new FormData();
    formData.append("uploadfile", file.file);
    if (file) {
      uploadImage(formData).then(({ data }) => {
        this.setState({ imageUrl: data.name });
      });
    }
  };
  getImageNameAndType = (name) => {
    if (name) {
      // debugger
      return name.split(".")[0] + "/" + name.split(".")[1];
    }
    return "";
  };
  changeSelected = (value, type) => {

    if (type === 'productCategory') {
      this.setState({ productCategory: value ? value : null }, () => {
      });
    }
    if (type === 'supplier') {
      this.setState({ supplier: value ? value : null }, () => {
      });
    }

  }
  handleImageSelect = (files) => {
    this.setState({ file: files })

  };
  handleImageRemove = () => {
    this.setState({
      file: null,
      imagePreviewUrl: "",
    });
  };
  handleSelectColor = (productColors) => {
    this.setState({ productColors: productColors }, function () {
    });
  }
  render() {
    let {
      id,
      code,
      name,
      mainImageUrl,
      images,
      imageUrl, files,
      noteAvatarImage,
      listColors, productColors
    } = this.state;
    let { open, handleClose, handleOKEditClose, t, i18n } = this.props;
    let isEmpty = files.length === 0;
    if (imageUrl) {
      isEmpty = false;
    }
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
            <Grid className="" container spacing={2}>
              <Grid
                item
                xs={12}
                sm={12}
                md={3}
                className={"container-create-category mt-16"}
              >
                <Grid item xs={12} sm={12} md={10}>
                  <div className="flex flex-wrap mb-16">
                    <label
                      htmlFor="upload-single-file"
                      className="w-100"
                    >
                      <Fab
                        className="capitalize"
                        color="secondary"
                        component="span"
                        variant="extended"
                        size="small"
                      >
                        <div className="flex flex-middle ">
                          <Icon className="pr-8">
                            cloud_upload
                                                    </Icon>
                          <span>
                            Chọn ảnh đại diện
                                                    </span>
                        </div>
                      </Fab>
                    </label>
                    <input
                      className="display-none"
                      onChange={this.handleFileSelect}
                      id="upload-single-file"
                      type="file"
                    />
                  </div>
                </Grid>
                <Grid
                  xs={12}
                  sm={12}
                  md={12}
                  className={"custom-image-article mb-16"}
                >
                  {!isEmpty && imageUrl && (
                    <span>
                      <Card className="" elevation={2}>
                        {/* {isEmpty && <p className="px-16 py-16">Que is empty</p>} */}
                        {/* var imageUrl = ConstantList.API_ENPOINT+"/public/file/downloadbyid/"+result.data.id; */}
                        <img
                          className="custom-image"
                          src={
                            ConstantList.API_ENPOINT +
                            "/public/getImage/" +
                            this.getImageNameAndType(
                              imageUrl
                            )
                          }
                        />
                      </Card>
                      <TextValidator
                        size="small"
                        className="w-100 mb-16 mt-16"
                        label={t(
                          "general.noteAvatarImage"
                        )}
                        onChange={this.handleChange}
                        type="text"
                        name="noteAvatarImage"
                        value={noteAvatarImage}
                        variant="outlined"
                        size="small"
                      />
                    </span>
                  )}
                </Grid>
              </Grid>
              <Grid item md={12} sm={12} xs={12}>
                <UploadImage
                  className="w-30"
                  handleImageSelect={this.handleImageSelect}
                  handleImageRemove={this.handleImageRemove}
                  mainImageUrl={mainImageUrl}
                  imagePreviewUrl={images}
                  t={t}
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
                  name="name"
                  value={name}
                  validators={["required"]}
                  errorMessages={[t("general.required")]}
                  variant="outlined"
                  size="small"
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
                  size="small"
                />
              </Grid>
              <Grid item sm={12} xs={12}>
                <TextValidator
                  className="w-100 "
                  label={
                    <span>
                      <span style={{ color: "red" }}>*</span>
                      {t("Giá Bán")}
                    </span>
                  }
                  onChange={this.handleChange}
                  type="text"
                  name="price"
                  value={this.state.price}
                  validators={["required"]}
                  errorMessages={[t("general.required")]}
                  variant="outlined"
                  size="small"
                />
              </Grid>
              <Grid item sm={12} xs={12}>
                <TextValidator
                  className="w-100 "
                  label={
                    <span>
                      <span style={{ color: "red" }}>*</span>
                      {t("Giá Bán hiện tại")}
                    </span>
                  }
                  onChange={this.handleChange}
                  type="text"
                  name="currentSellingPrice"
                  value={this.state.currentSellingPrice}
                  validators={["required"]}
                  errorMessages={[t("general.required")]}
                  variant="outlined"
                  size="small"
                />
              </Grid>
              <Grid item sm={12} xs={12}>
                <Autocomplete
                  id="combo-box"
                  value={this.state.productCategory ? this.state.productCategory : null}
                  renderInput={(params) => <TextValidator {...params}
                    value={this.state.productCategory ? this.state.productCategory : null}
                    label={<span ><span style={{ color: "red" }}></span>{t('Danh mục sản phẩm')}</span>}
                    variant="outlined"
                    size="small"
                  />}
                  options={this.state.listDM ? this.state.listDM : []}
                  getOptionLabel={(option) => option.name}
                  getOptionSelected={(option, value) =>
                    option.id === value.id
                  }
                  onChange={(event, value) => { this.changeSelected(value, 'productCategory') }}
                />
              </Grid>
              <Grid item sm={12} xs={12}>
                <Autocomplete
                  id="combo-box"
                  value={this.state.supplier ? this.state.supplier : null}
                  renderInput={(params) => <TextValidator {...params}
                    value={this.state.supplier ? this.state.supplier : null}
                    label={<span ><span style={{ color: "red" }}></span>{t('Nhà cung cấp')}</span>}
                    variant="outlined"
                    size="small"
                  />}
                  options={this.state.listSupplier ? this.state.listSupplier : []}
                  getOptionLabel={(option) => option.name}
                  getOptionSelected={(option, value) =>
                    option.id === value.id
                  }
                  onChange={(event, value) => { this.changeSelected(value, 'supplier') }}
                />
              </Grid>
              <Grid item md={12} sm={12} xs={12} className="mt-10">
                {listColors && (<Autocomplete
                  variant="outlined"
                  size="small"
                  style={{ width: '100%' }}
                  multiple
                  id="combo-box-demo"
                  defaultValue={productColors}
                  value={productColors}
                  options={listColors}
                  getOptionSelected={(option, value) => option.id === value.id}
                  getOptionLabel={(option) => option.name}
                  onChange={(event, value) => {
                    this.handleSelectColor(value);
                  }}
                  renderInput={(params) =>
                    <TextValidator
                      {...params}
                      value={productColors}
                      label={
                        <span className="font">
                          <span style={{ color: "red" }}> * </span>
                          Màu sản phẩm
                        </span>
                      }
                      fullWidth
                      validators={["required"]}
                      errorMessages="Vui lòng chọn màu của sản phẩm"
                      variant="outlined"
                      size="small"
                    />}

                />)}
              </Grid>
              <Grid item sm={12} xs={12}>
                <Button
                  size="small"
                  style={{ float: "right" }}
                  className=" mt-16"
                  variant="contained"
                  color="primary"
                  onClick={() => {
                    this.setState({ shouldOpenSelectAgencyPopup: true })
                  }}
                >
                  {t("general.select")}
                </Button>
                <TextValidator
                  size="small"
                  InputLabelProps={{ shrink: true }}
                  label={
                    <span>
                      <span style={{ color: "red" }}></span>
                      {t("Đơn vị tính")}
                    </span>
                  }
                  style={{ width: "80%" }}
                  value={
                    this.state.agency != null ? this.state.agency.name : ""
                  }
                  variant="outlined"
                  size="small"
                />

                {this.state.shouldOpenSelectAgencyPopup && (
                  <SelectAgencyPopup
                    open={this.state.shouldOpenSelectAgencyPopup}
                    handleSelect={this.handleSelectAgency}
                    selectedItem={
                      this.state.agency != null
                        ? this.state.agency
                        : {}
                    }
                    handleClose={this.handleDialogClose}
                    t={t}
                    i18n={i18n}
                  />
                )}
              </Grid>

              <Grid item sm={12} xs={12}>
                <EditorForm
                  content={this.state.posts ? this.state.posts : ""}
                  handleChangeContent={this.handleChangeContent}
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

export default AgentDialog;
