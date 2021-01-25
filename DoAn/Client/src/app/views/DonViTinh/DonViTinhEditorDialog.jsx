import React, { Component } from "react";
import {
  Dialog,
  Button,
  Grid,
  FormControlLabel,
  Switch, DialogActions
} from "@material-ui/core";
import { ValidatorForm, TextValidator } from "react-material-ui-form-validator";
import { getAllStockKeepingUnits, updateStockKeepingUnit,addNewStockKeepingUnit, searchByPage, checkCode } from './DonViTinhService';
import DialogTitle from '@material-ui/core/DialogTitle';
import DialogContent from '@material-ui/core/DialogContent';
import Draggable from 'react-draggable';
import Paper from '@material-ui/core/Paper';
import NotificationPopup from '../Component/NotificationPopup/NotificationPopup'
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
class DonViTinhEditorDialog extends Component {
  state = {
    ten: "",
    ma: "",
    shouldOpenNotificationPopup: false
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
    let { id} = this.state;
    let { t } = this.props;
    if (id) {
      updateStockKeepingUnit({
        ...this.state
      }).then((response) => {
        if (response.data && response.status == 200) {
          toast.info(t('StockKeepingUnit.noti.updateSuccess'));
          this.props.handleOKEditClose();
        } else {
          toast.error(t('StockKeepingUnit.noti.addFail'));
        }
      });
    } else {
      addNewStockKeepingUnit({
        ...this.state
      }).then((response) => {
        if (response.data && response.status == 200) {
          toast.info(t('StockKeepingUnit.noti.addSuccess'));
          this.props.handleOKEditClose();
        } else {
          toast.error(t('StockKeepingUnit.noti.addFail'));
        }
      });
    }
};

componentWillMount() {

}
componentDidMount() {
  let { open, handleClose, item } = this.props;
  this.setState({
    ...this.props.item
  }, function () {

  }
  );
}

handleDialogClose = () => {
  this.setState({ shouldOpenNotificationPopup: false, })
}

render() {
  let { open, handleClose, handleOKEditClose, t, i18n } = this.props;
  let {
    id,
    ten,
    ma,
    shouldOpenNotificationPopup
  } = this.state;
  return (
    <Dialog open={open} PaperComponent={PaperComponent} maxWidth="md">
      {shouldOpenNotificationPopup && (
        <NotificationPopup
          title={t('general.noti')}
          open={shouldOpenNotificationPopup}
          // onConfirmDialogClose={this.handleDialogClose}
          onYesClick={this.handleDialogClose}
          text={t(this.state.Notification)}
          agree={t('general.agree')}
        />
      )}
      <DialogTitle style={{ cursor: 'move' }} id="draggable-dialog-title">
        <h4 className="mb-20">{t('general.saveUpdate')}</h4>
      </DialogTitle>
      <ValidatorForm ref="form" onSubmit={this.handleFormSubmit}>
        <DialogContent>
          <Grid className="mb-16" container spacing={1}>
            <Grid item md={12} sm={12} xs={12}>
              <TextValidator
                className="w-100 mb-16"
                label={<span><span style={{ color: "red" }}>*</span>{t('StockKeepingUnit.code')}</span>}
                onChange={this.handleChange}
                type="text"
                name="ma"
                value={ma}
                validators={["required"]}
                errorMessages={["This field is required"]}
              />
            </Grid>
            <Grid item md={12} sm={12} xs={12}>
              <TextValidator
                className="w-100 mb-16"
                label={<span><span style={{ color: "red" }}>*</span>{t('StockKeepingUnit.name')}</span>}
                onChange={this.handleChange}
                type="text"
                name="ten"
                value={ten}
                validators={["required"]}
                errorMessages={["This field is required"]}
              />
            </Grid>
          </Grid>
        </DialogContent>
        <DialogActions>
          <div className="flex flex-space-between flex-middle">
            <Button
              variant="contained"
              className="mr-36"
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

export default DonViTinhEditorDialog;
