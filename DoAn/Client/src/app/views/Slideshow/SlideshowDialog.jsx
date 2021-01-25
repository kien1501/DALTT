import React, { Component } from "react";

import {
  IconButton,
  Dialog,
  Button,
  Icon,
  Grid,
  DialogActions,
  Tooltip
} from "@material-ui/core";
import { ValidatorForm,TextValidator } from "react-material-ui-form-validator";
import UploadImage from "../forms/UploadImage";
import { editImageSlide, createImageSlide,addNewData } from "./SlideshowService";
import axios from "axios";
import ConstantList from "../../appConfig";
import DialogContent from "@material-ui/core/DialogContent";
import Draggable from "react-draggable";
import Paper from "@material-ui/core/Paper";
import { ConfirmationDialog } from "egret";
import Loading from "../../../egret/components/EgretLoadable/Loading";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import localStorageService from "../../../app/services/localStorageService";
import {searchByPage as getSP} from '../SanPham/SanPhamService'
import Autocomplete from "@material-ui/lab/Autocomplete";
toast.configure();
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

class SlideShowDialog extends Component {
  constructor(props) {
    super(props);
    this.state = {
      name: "",
      page: 0,
      mainImageUrl: "",
      imagePreviewUrl: "",
      loading: false,
    };
  }

  handleChange = (event, source) => {
    event.persist();
    this.setState({
      [event.target.name]: event.target.value,
    });
  };

  handleDialogClose = () => {
    this.setState({
      shouldOpenEditorDialog: false,
      shouldOpenDialog: false,
      shouldOpenConfirmationDialog: false,
      shouldOpenConfirmationDeleteAllDialog: false,
      shouldOpenShowDesDialog: false,
    });
  };
  handleChangePage = (event, newPage) => {
    this.setPage(newPage);
  };

  handleFormSubmit = () => {
    console.log(this.props);
    // if(this.state.product == null){
    //   toast.warning("Chưa chọn sản phẩm");
    //   return
    // }
    this.setState({ loading: true });
    
     
      addNewData({
        ...this.state,
      })
        .then((res) => {
          if (this.state.file !== null) {
            this.setState(
              {
                loading: false,
                shouldOpenDialog: true,
                // productId: res.data.id,
              },
              () => {
              }
            );
    
            for (var i = 0; i < this.state.file.length; i++) { 
              console.log("WWWWWWWWWWWWWW)");
              const url = ConstantList.API_ENPOINT + "/api/upload/imageSlide";
              let formData = new FormData();
              formData.append('file', this.state.file[i]);
              formData.append('slideShow', res.data.id);
              const config = {
                headers: {
                  'Content-Type': 'multipart/form-data'
                }
              }
               axios.post(url, formData, config)
            }


          }
        })
        .then((data) => {
          var { t, i18n } = this.props;
          toast.success(t("general.success"));
          // this.props.handleOKEditClose()
        });
     
  };

  componentWillMount() {
    this.setState({
      ...this.props.item,
    });
    var searchObject = {};
        searchObject.pageIndex = 1;
        searchObject.pageSize = 100000;
        getSP(searchObject).then(res => {
          this.setState({ listSP: [...res.data.content] })
        })
  }

  handleImageSelect = (files) => {
    this.setState({ file: files });
  };

  componentDidMount() {
    let user = localStorageService.getItem("auth_user");
    this.setState({ userId: user.id });
  }

  handleOKEditClose = () => {
    this.setState(
      {
        shouldOpenEditorDialog: false,
        shouldOpenConfirmationDialog: false,
      },
      () => {
        this.props.handleClose();
      }
    );
  };

  handleImageRemove = () => {
    this.setState({
      file: null,
      imagePreviewUrl: "",
    });
  };
  changeSelected = (value, type) =>{

    if(type === 'product'){
      this.setState({product:value ? value:null,productId:value ? value.id:""},()=>{
      });
    }
    
    
  }
  render() {
    let { mainImageUrl, loading, images } = this.state;
    let { open, t } = this.props;

    return (
      <Dialog
        open={open}
        PaperComponent={PaperComponent}
        maxWidth="md"
        fullWidth={true}
      >
        <ValidatorForm ref="form" onSubmit={this.handleFormSubmit}>
          <div
            style={{ cursor: "move" }}
            id="draggable-dialog-title"
            className="flex flex-space-between flex-middle pl-16 pr-8 py-8 bg-primary"
          >
            <h4 className="m-0 text-white">
              {this.props.isClone
                ? t("SlideShow.clone")
                : this.state.id
                ? t("SlideShow.update")
                : t("Thêm")}
            </h4>
            <IconButton onClick={this.props.handleClose}>
              <Icon className="text-white">clear</Icon>
            </IconButton>
          </div>
          <DialogContent>
            <Grid className="mb-10" container spacing={3}>
            {/* <Grid item sm={12} xs={12}>          
                      <Autocomplete                  
                          id="combo-box"
                          value={this.state.product ?this.state.product: null}
                          renderInput={(params) => <TextValidator {...params}
                              value={this.state.product? this.state.product: null}
                              label = {<span ><span style={{color:"red"}}></span>{t('Sản phẩm')}</span>}
                              variant ="outlined"
                              size ="small"
                          />}
                          options={this.state.listSP ? this.state.listSP:[]}
                          getOptionLabel = {(option) => option.code}
                          getOptionSelected={(option, value) =>
                            option.id === value.id
                          }
                          onChange={(event,value) => {this.changeSelected(value,'product')}}
                      />
              </Grid> */}
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
            </Grid>
          </DialogContent>
          <DialogActions>
            <div className="flex flex-space-between flex-middle mt-36">
              <Button
                variant="contained"
                color="secondary"
                className="mr-36"
                onClick={() => this.props.handleClose()}
                disabled={this.state.loading}
              >
                {t("general.cancel")}
              </Button>
              {this.state.loading ? (
                <Loading />
              ) : (
                <Button
                  variant="contained"
                  color="primary"
                  type="submit"
                  disabled={loading}
                >
                  {t("general.save")}
                </Button>
              )}
            </div>
          </DialogActions>
        </ValidatorForm>

        {this.state.shouldOpenDialog && (
          <ConfirmationDialog
            open={this.state.shouldOpenDialog}
            onConfirmDialogClose={this.props.handleClose}
            onYesClick={this.handleOKEditClose()}
            text={t("SlideShow.add_new_offer_question")}
            agree={t("general.agree")}
            cancel={t("general.cancel")}
          />
        )}
      </Dialog>
    );
  }
}

export default SlideShowDialog;
