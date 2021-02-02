import React, { Component } from 'react';
import ConstantList from "../../appConfig";
import {
  Dialog,
  Button,
  Grid,
  DialogActions,
  FormControlLabel,
  Switch,
  DialogTitle,
  DialogContent,
  Icon,Fab,Card,
  IconButton,
} from "@material-ui/core";
import { ValidatorForm, TextValidator } from "react-material-ui-form-validator";
import { generateRandomId } from "utils";
import Draggable from "react-draggable";
import Paper from "@material-ui/core/Paper";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "../../../styles/views/_style.scss";
import EditorForm from "./EditorForm";
import { addNew, update } from "./IntroduceTheProgramService";
import IntroduceTheProgramDialogTab from "./IntroduceTheProgramDialogTab";
import Checkbox from '@material-ui/core/Checkbox';
import { uploadImage } from "../SanPham/SanPhamService";


class IntroduceTheProgramDialogInfomation extends Component {

  componentWillMount() {
    let { open, handleClose, item ,isRoleAdmin,isView } = this.props;
    this.setState({
      item: item,imageUrl: "",files: [],
    })
  }

    handleChange = (event, source) => {
        let {item} = this.state
        if(item == null){
          item = {}
        }
        let name = event.target.name
        let value = event.target.value
        if (source == "checked") {
          item["active"] = event.target.checked;
          this.setState({item:item})
          return
        }
        item[name] = value
        this.setState({
          item: item
        });
    
      };
      handleChangeContent = (content) => {
        let {item} = this.state
        item["content"] = content;
        this.setState({ item: item });
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
                    let {item} = this.state
                    if(item == null){
                      item = {}
                    }
                    item["imageUrl"] =data.name
                    this.setState({ imageUrl: data.name,item:item });
                  });
                }
              }
            );
          }
        }
      };
      getImageNameAndType = (name) => {
        if (name) {
          // debugger
          return name.split(".")[0] + "/" + name.split(".")[1];
        }
        return "";
      };
    render() {
        let {t,i18n} = this.props;
        let {item,mainImageUrl,
          images,
          imageUrl, files,} = this.state;
          let isEmpty = files.length === 0;
            if (item.imageUrl) {
              isEmpty = false;
            }
        return (
            <div>
                <Grid className="mb-8" container spacing={4}>
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
                  {!isEmpty && item.imageUrl && (
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
                              item.imageUrl
                            )
                          }
                        />
                      </Card>

                    </span>
                  )}
                </Grid>
              </Grid>
              <Grid item lg={12} md={12} sm={12} xs={12}>
                <TextValidator
                  className="w-100 mb-16"
                  label={
                    <span className="font">
                      <span style={{ color: "red" }}> * </span>
                      {t("Code")}
                    </span>
                  }
                  onChange={this.handleChange}
                  type="text"
                  name="code"
                  value={item.code ? item.code : ""}
                  validators={["required"]}
                  errorMessages={[t("general.errorMessages_required")]}
                  variant="outlined"
                  size="small"
                />
                <TextValidator
                  className="w-100 mb-16"
                  label={
                    <span className="font">
                      <span style={{ color: "red" }}> * </span>
                      {t("Name")}
                    </span>
                  }
                  onChange={this.handleChange}
                  type="text"
                  name="name"
                  value={item.name ? item.name : ""}
                  validators={["required"]}
                  errorMessages={[t("general.errorMessages_required")]}
                  variant="outlined"
                  size="small"
                />
                {/* <FormControlLabel
                  control={
                    <Checkbox
                      checked={item.active ? true : false}
                      onChange={(e) => this.handleChange(e, "checked")}
                      name="checkedB"
                      color="primary"
                    />
                  }
                  label={t("isShow")}
                /> */}
                <EditorForm
                  content={item.content ? item.content : ""}
                  handleChangeContent={this.handleChangeContent}
                />
              </Grid>
            </Grid>
            </div>
        );
    }
}

export default IntroduceTheProgramDialogInfomation;