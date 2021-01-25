import React, { Component } from "react";
import {
  Dialog,
  Button,
  Grid,
  DialogActions
} from "@material-ui/core";
import { ValidatorForm, TextValidator } from "react-material-ui-form-validator";
import { getAllStores, addNewStore, updateStore, searchByPage, checkCode } from './KhoService';
import DialogTitle from '@material-ui/core/DialogTitle';
import DialogContent from '@material-ui/core/DialogContent';
import Draggable from 'react-draggable';
import Paper from '@material-ui/core/Paper';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
toast.configure();

function PaperComponent(props) {
  return (
    <Draggable handle="#draggable-dialog-title" cancel={'[class*="MuiDialogContent-root"]'}>
      <Paper {...props} />
    </Draggable>
  );
}
class StoreEditorDialog extends Component {
  state = {
    tenKho: "",
    maKho: "",
    diaChi: ""
  };

  handleChange = (event, source) => {
    event.persist();
    if (source === "switch") {
      this.setState({ isActive: event.target.checked });
      return;
    }
    this.setState({
      [event.target.name]: event.target.value
    });
  };

  handleFormSubmit = () => {
    let { id } = this.state;
    let { t } = this.props;
    if (id) {
      updateStore({
        ...this.state
      }).then((response) => {
        if (response.data && response.status == 200) {
          toast.info(t('Store.noti.updateSuccess'));
          this.props.handleOKEditClose();
        } else {
          toast.error(t('Store.noti.addFail'));
        }
      });
    } else {
      addNewStore({
        ...this.state
      }).then((response) => {
        if (response.data && response.status == 200) {
          toast.info(t('Store.noti.addSuccess'));
          this.props.handleOKEditClose();
        } else {
          toast.error(t('Store.noti.addFail'));
        }
      });
    }
  };

  componentWillMount() {
    let { open, handleClose, item } = this.props;
    this.setState({
      ...this.props.item
    }, function () {

    }
    );
  }
  componentDidMount() {
  }

  render() {
    let { open, handleClose, handleOKEditClose, t, i18n } = this.props;
    let {
      name,
      code,
      address
    } = this.state;

    return (
      <Dialog open={open} PaperComponent={PaperComponent} maxWidth="md">
        <DialogTitle style={{ cursor: 'move' }} id="draggable-dialog-title">
          <span className="mb-20">{t('general.saveUpdate')}</span>
        </DialogTitle>
        <ValidatorForm ref="form" onSubmit={this.handleFormSubmit}>
          <DialogContent>
            <Grid className="mb-16" container spacing={1}>
              <Grid item md={12} sm={12} xs={12}>
                <TextValidator
                  className="w-100 mb-16"
                  label={<span><span style={{ color: "red" }}>*</span>{t('Store.code')}</span>}
                  onChange={this.handleChange}
                  type="text"
                  name="code"
                  value={code}
                  validators={["required"]}
                  errorMessages={["This field is required"]}
                />
              </Grid>
              <Grid item md={12} sm={12} xs={12}>
                <TextValidator
                  className="w-100 mb-16"
                  label={<span><span style={{ color: "red" }}>*</span>{t('Store.name')}</span>}
                  onChange={this.handleChange}
                  type="text"
                  name="name"
                  value={name}
                  validators={["required"]}
                  errorMessages={["This field is required"]}
                />
              </Grid>
              <Grid item md={12} sm={12} xs={12}>
                <TextValidator
                  className="w-100 mb-16"
                  label={<span><span style={{ color: "red" }}>*</span>{t('Store.address')}</span>}
                  onChange={this.handleChange}
                  type="text"
                  name="address"
                  value={address}
                  validators={["required"]}
                  errorMessages={["This field is required"]}
                />
              </Grid>
            </Grid>
          </DialogContent>
          <DialogActions>
            <div className="flex flex-space-between flex-middle">
              <Button
                className="mr-36"
                variant="contained"
                color="secondary"
                onClick={() => this.props.handleClose()}
              >
                {t('general.cancel')}
              </Button>
              <Button
                variant="contained"
                color="primary"
                type="submit"
              >
                {t('general.save')}
              </Button>

            </div>
          </DialogActions>
        </ValidatorForm>

      </Dialog>
    );
  }
}

export default StoreEditorDialog;
