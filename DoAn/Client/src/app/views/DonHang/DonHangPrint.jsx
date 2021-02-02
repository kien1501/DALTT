import React, { Component, useRef } from "react";
import {
  Dialog,
  Button,
  Grid,
  Checkbox,
  IconButton,
  Icon,
  DialogActions,
} from "@material-ui/core";
import { ValidatorForm, TextValidator } from "react-material-ui-form-validator";
import MaterialTable, {
  MTableToolbar,
  Chip,
  MTableBody,
  MTableHeader,
} from "material-table";
import DialogTitle from "@material-ui/core/DialogTitle";
import DialogContent from "@material-ui/core/DialogContent";
import { useTranslation, withTranslation, Trans } from "react-i18next";
import Draggable from "react-draggable";
import Paper from "@material-ui/core/Paper";
import { divide } from "lodash";
import moment from "moment";
import html2canvas from "html2canvas";
import { jsPDF } from "jspdf";
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

function MaterialButton(props) {
  const { t, i18n } = useTranslation();
}

class DonHangPrint extends React.Component {
  state = {
    AssetAllocation: [],
    item: {},
    asset: {},
    assetVouchers: [],
    shouldOpenEditorDialog: false,
    shouldOpenViewDialog: false,
    shouldOpenConfirmationDialog: false,
    selectAllItem: false,
    selectedList: [],
    totalElements: 0,
    shouldOpenConfirmationDeleteAllDialog: false,
  };

  componentWillMount() {
    let { open, handleClose, item } = this.props;
    // if (item && item.productOrder && item.productOrder.length > 0) {
    //   item.details.sort((a, b) =>
    //     a.orderNumber > b.orderNumber
    //       ? 1
    //       : a.orderNumber === b.orderNumber
    //       ? a.sampleTube.code > b.sampleTube.code
    //         ? 1
    //         : -1
    //       : -1
    //   );
    // }
    this.setState(
      {
        ...this.props.item,
      },
      function () {
      }
    );

    // this.setState({})
  }

  componentDidMount() {}

  handleFormSubmit = () => {
    let content = document.getElementById("divcontents");
    let pri = document.getElementById("ifmcontentstoprint").contentWindow;
    pri.document.open();

    pri.document.write(content.innerHTML);

    pri.document.close();
    pri.focus();
    pri.print();
  };
  // handleExportPdf = () => {
  //   let content = document.getElementById("divcontents");
  //   html2canvas(content).then((canvas) => {
  //     const imgData = canvas.toDataURL("image/png");
  //     const pdf = new jsPDF();
  //     pdf.addImage(imgData, "JPEG", 0, 0);
  //     // pdf.output('dataurlnewwindow');
  //     pdf.save("download.pdf");
  //   });
  //   this.props.handleOKEditClose();
  // };

  render() {
    const { t, i18n } = this.props;
    let { open, handleClose, handleOKEditClose, item } = this.props;
    let now = new Date();

    return (
      <Dialog
        open={open}
        PaperComponent={PaperComponent}
        maxWidth="md"
        fullWidth
      >
        <DialogTitle
          style={{ cursor: "move" }}
          id="draggable-dialog-title"
        ></DialogTitle>
        <iframe
          id="ifmcontentstoprint"
          style={{ height: "0px", width: "0px", position: "absolute" }}
        ></iframe>
        <ValidatorForm ref="form" onSubmit={this.handleFormSubmit}>
          <DialogContent
            id="divcontents"
            style={{
              width: "210mm",
              minHeight: "auto",
              marginLeft: "auto",
              marginRight: "auto",
            }}
          >
            <div style={{ textAlign: "center" }}>
              <div>
                <p
                  style={{
                    fontSize: "0.975rem",
                    fontWeight: "bold",
                    marginBottom: "0px",
                  }}
                >
                  HÓA ĐƠN
                </p>
              </div>
              <div style={{ textAlign: "left" }}>
                <ol>
                <li>
                    <p>
                      {t("Tên khách hàng")} :{" "}
                      {this.state.customer
                        ? this.state.customer.name
                        : ""}
                    </p>
                  </li>
                  <li>
                    <p>
                      {t("SĐT khách hàng")} :{" "}
                      {this.state.customer
                        ? this.state.customer.phoneNumber
                        : ""}
                    </p>
                  </li>
                  <li>
                    <p>
                      {t("Địa chỉ")} :{" "}
                      {this.state.customer
                        ? this.state.customer.fullAddress
                        : ""}
                    </p>
                  </li>
                  <li>
                    <p>
                      {t("Mã hóa đơn")} :{" "}
                      {this.state.code
                        ? this.state.code
                        : ""}
                    </p>
                  </li>
                  <li>
                    <p>
                      {t("Tên hóa đơn")} :{" "}
                      {this.state.name ? this.state.name : ""}{" "}
                    </p>
                  </li>
                  <li>
                    <p>
                      {t("Ngày đặt hàng")} :{" "}
                      {this.state.orderDate
                        ? moment(this.state.orderDate).format(
                            "DD/MM/YYYY"
                          )
                        : ""}
                    </p>
                  </li>
                  <li>
                    <p>
                      {t("Ngày giao hàng")} :{" "}
                      {this.state.deliveryDate ? (
                        <span>
                          {moment(this.state.deliveryDate).format(
                            "DD/MM/YYYY"
                          )}
                        </span>
                      ) : (
                        ""
                      )}
                    </p>
                  </li>
                  <li>
                    <p>
                      {t("Tổng tiền")} :{" "}
                      {this.state.totalPrice ? this.state.totalPrice : ""}{" "}
                    </p>
                  </li>
                  <li>
                    <p>
                      {t("Giảm giá")} :{" "}
                      {this.state.discount
                        ? this.state.discount
                        : ""}
                    </p>
                  </li>
                  <li>
                    <p>
                      {t("Thành tiền")} :{" "}
                      {this.state.intoMoney ? this.state.intoMoney : ""}
                    </p>
                  </li>
                  <li>{t("Danh sách sản phẩm") + ": "}</li>
                </ol>
              </div>
              <div>
                {
                  <table
                    style={{
                      width: "100%",
                      border: "1px solid",
                      borderCollapse: "collapse",
                    }}
                  >
                    <tr>
                      <th style={{ border: "1px solid",width: "16%" }}>
                        {t("Tên sản phẩm")}
                      </th>
                      <th style={{ border: "1px solid",width: "16%" }}>
                        {t("Màu sản phẩm")}
                      </th>
                      <th style={{ border: "1px solid",width: "16%" }}>
                        {t("Đơn giá")}
                      </th>
                      <th style={{ border: "1px solid", width: "16%" }}>
                        {t("Số lượng")}
                      </th>
                      <th style={{ border: "1px solid", width: "17%" }}>
                        {t("Giảm giá")}
                      </th>
                      <th style={{ border: "1px solid", width: "17%" }}>
                        {t("Thành tiền")}
                      </th>
                      
                    </tr>

                    {this.state.productOrder !== null
                      ? this.state.productOrder.map((row, index) => {
                          return (
                            <tr>
                              <td
                                style={{
                                  border: "1px solid",
                                  textAlign: "center",
                                }}
                              >
                                {row.productColor !== null
                                  ? row.productColor.product.name
                                  : ""}
                              </td>
                              <td
                                style={{
                                  border: "1px solid",
                                  textAlign: "center",
                                }}
                              >
                                {row.productColor !== null
                                  ? row.productColor.color.name
                                  : ""}
                              </td>
                              <td
                                style={{
                                  border: "1px solid",
                                  textAlign: "center",
                                }}
                              >
                                {row.unitPrice !== null ? row.unitPrice : ""}
                              </td>
                              <td
                                style={{
                                  border: "1px solid",
                                  textAlign: "center",
                                }}
                              >
                                {row.productNumber !== null ? row.productNumber : ""}
                              </td>
                              <td
                                style={{
                                  border: "1px solid",
                                  textAlign: "center",
                                }}
                              >
                                {row.discount !== null ? row.discount : ""}
                              </td>
                              <td
                                style={{
                                  border: "1px solid",
                                  textAlign: "center",
                                }}
                              >
                                {row.intoMoney !== null ? row.intoMoney : ""}
                              </td>
                            </tr>
                          );
                        })
                      : ""}
                  </table>
                }
              </div>
            </div>
          </DialogContent>

          <DialogActions>
            <div className="flex flex-space-between flex-middle">
              <Button
                variant="contained"
                color="secondary"
                className="mr-12"
                onClick={() => this.props.handleClose()}
              >
                {t("general.cancel")}
              </Button>
              {(
                <Button variant="contained" color="primary" type="submit">
                  {t("In")}
                </Button>
              )}

              {this.props.pdf && (
                <Button
                  variant="contained"
                  color="primary"
                  onClick={this.handleExportPdf}
                >
                  {t("Xuất PDF")}
                </Button>
              )}
              {/* <Example></Example> */}
            </div>
          </DialogActions>
        </ValidatorForm>
      </Dialog>
    );
  }
}

export default DonHangPrint;
