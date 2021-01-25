import { Grid, MuiThemeProvider, TextField, Button, TableHead, TableCell, TableRow, Checkbox, TablePagination, Radio, Dialog, DialogActions } from "@material-ui/core";
import { createMuiTheme } from "@material-ui/core/styles";
import React, { Component } from "react";
import ReactDOM from "react-dom";
import MaterialTable, { MTableToolbar, Chip, MTableBody, MTableHeader } from 'material-table';
import { useTranslation, withTranslation, Trans } from 'react-i18next';
import { ValidatorForm, TextValidator } from "react-material-ui-form-validator";
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';
import Draggable from 'react-draggable';
import Paper from '@material-ui/core/Paper';
//import { searchByPage } from "../CmsCategory/CmsCategoryService";
import {getListFile,searchByPage} from "./FileService";
function PaperComponent(props) {
  return (
    <Draggable handle="#draggable-dialog-title" cancel={'[class*="MuiDialogContent-root"]'}>
      <Paper {...props} />
    </Draggable>
  );
}
class FileBrowserDialog extends React.Component {
  constructor(props) {
    super(props);
    this.handleChange = this.handleChange.bind(this);
  }
  state = {
    rowsPerPage: 5,
    page: 0,
    data: [],
    totalElements: 0,
    itemList: [],
    shouldOpenEditorDialog: false,
    shouldOpenConfirmationDialog: false,
    selectedItem: {},
    selectedValue:'',
    keyword: '',
    shouldOpenProductDialog: false,
        assetList:[]
  };

  setPage = page => {
    this.setState({ page }, function () {
      this.updatePageData();
    })
  };

  setRowsPerPage = event => {
    this.setState({ rowsPerPage: event.target.value, page: 0 }, function () {
      this.updatePageData();
    })
  };

  handleChangePage = (event, newPage) => {
    this.setPage(newPage);
  };

  updatePageData = () => {
    let status = 0;
    var searchObject = {};      
      searchObject.keyword = this.state.keyword;
      searchObject.pageIndex = this.state.page +1;
      searchObject.pageSize = this.state.rowsPerPage;
      searchByPage(searchObject).then(({ data }) => {
        let itemListClone = [...data.content]
      itemListClone.map( item => {
        if(this.state.categories){
          this.state.categories.forEach( categories => {
            if(categories.category.id === item.id) {
              item.isCheck = true;
            }
          })   
        }     
      })
      this.setState({ itemList: [...itemListClone], totalElements: data.totalElements },()=>{
        
      })
      });
  }

  componentDidMount() {
    this.updatePageData();
  }

  handleClick = (event, item) => {
    if (item.id != null) {
      this.setState({ selectedValue: item.id, selectedItem: item })
    } else {
      this.setState({ selectedValue: null, selectedItem: null })
    }
  }

  componentWillMount() {
    let { open, handleClose, selectedItem, categories} = this.props;
    //this.setState(item);
    this.setState({ categories:categories });
  }

  handleKeyDownEnterSearch = e => {
    if (e.key === 'Enter') {
      this.search();
    }
  };
  handleChange = (event) => {
    this.setState({selectedValue:event.target.value});
  };

  search() {
    this.setPage(0, function () {
      let status = 0;
      var searchObject = {};
      searchObject.keyword = this.state.keyword;
      searchObject.pageIndex = this.state.page;
      searchObject.pageSize = this.state.rowsPerPage;
      searchByPage(searchObject).then(({ data }) => {
        this.setState({ itemList: [...data.content], totalElements: data.totalElements })
      });
    });
  };

  handleChange = (event, source) => {
    event.persist();
    this.setState({
      [event.target.name]: event.target.value
    });
  };

  handleOpenProductDialog = () => {
    this.setState({
      shouldOpenProductDialog: true
    })
  }

  handleDialogProductClose = () => {
    this.setState({
      shouldOpenProductDialog: false
    })
  }

  handleOKEditClose = () => {
    this.setState({
      shouldOpenProductDialog: false
    });
    this.updatePageData();
  };

  onClickRow = (selectedRow) => {
    document.querySelector(`#radio${selectedRow.id}`).click();
  }

  render() {
    const { t, i18n, handleClose, handleSelect, selectedItem, open } = this.props;
    let { keyword, shouldOpenProductDialog, itemList, categories } = this.state;
    let columns = [
      {
        title: t("general.select"),
        field: "custom",
        align: "left",
        width: "250",
        render: (rowData) => (
          <Radio
            id={`radio${rowData.id}`}
            name="radSelected"
            value={rowData.id}
            checked={this.state.selectedValue === rowData.id}
            onClick={(event) => this.handleClick(event, rowData)}
          />
        ),
      },
      { title: t("Categories.title"), field: "name", align: "left", width: "150" },
      { title: t("Categories.slug"), field: "contentSize", align: "left", width: "150" },
    ];
    return (
      <Dialog onClose={handleClose} open={open} PaperComponent={PaperComponent} maxWidth={'md'} fullWidth>
        <DialogTitle style={{ cursor: 'move' }} id="draggable-dialog-title">
          <span className="mb-20">{t("component.asset.title")}</span>
        </DialogTitle>
        <DialogContent>
          <Grid item xs={12}>
            <TextValidator
              label={t('general.enterSearch')}
              className="mb-16 mr-10 w-60"
              name="keyword"
              value={keyword}
              onKeyDown={this.handleKeyDownEnterSearch}
              onChange={this.handleChange} />
            <Button
              className="mb-16 mr-16 align-bottom"
              variant="contained"
              color="primary"
              onClick={() => this.search()}>
              {t('general.search')}
            </Button>
              {/* { itemList.length !== 0 ? '' :
                <Button className="mb-16 mr-16 align-bottom"
                  variant="contained"
                  color="primary"
                  onClick={() => this.handleOpenProductDialog()}
                  >
                  {t('Product.add')}
                </Button>
              } */}
          </Grid>
          <Grid item xs={12}>
            <MaterialTable
            
              data={this.state.itemList}
              columns={columns}

              onRowClick={((evt, selectedRow) => this.onClickRow(selectedRow))}

              parentChildData={(row, rows) => {
                var list = rows.find(a => a.id === row.parentId);
                return list;
              }}
              options={{
                toolbar: false,
                selection: false,
                actionsColumnIndex: -1,
                paging: false,
                search: false
              }}
              components={{
                Toolbar: props => (
                  <div style={{ witdth: "100%" }}>
                    <MTableToolbar {...props} />
                  </div>
                ),
              }}
              onSelectionChange={(rows) => {
                this.data = rows;
              }}
            />
            <TablePagination
              align="left"
              className="px-16"
              rowsPerPageOptions={[1, 2, 3, 5, 10, 25]}
              component="div"
              count={this.state.totalElements}
              rowsPerPage={this.state.rowsPerPage}
              page={this.state.page}
              labelRowsPerPage={t('general.rows_per_page')}
              labelDisplayedRows={ ({ from, to, count }) => `${from}-${to} ${t('general.of')} ${count !== -1 ? count : `more than ${to}`}`}
              backIconButtonProps={{
                "aria-label": "Previous Page"
              }}
              nextIconButtonProps={{
                "aria-label": "Next Page"
              }}
              onChangePage={this.handleChangePage}
              onChangeRowsPerPage={this.setRowsPerPage}
            />
          </Grid>
        </DialogContent>
        <DialogActions>
          <Button
            className="mb-16 mr-36 align-bottom"
            variant="contained"
            color="secondary"
            onClick={() => handleClose()}>{t('general.cancel')}</Button>
          <Button className="mb-16 mr-16 align-bottom"
            variant="contained"
            color="primary"
            onClick={() => handleSelect(this.state.selectedItem)}>
            {t('general.select')}
          </Button>
        </DialogActions>
      </Dialog>
    )
  }
}
export default FileBrowserDialog;