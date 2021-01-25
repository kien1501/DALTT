import axios from "axios";
import ConstantList from "../../appConfig";
const API_PATH = ConstantList.API_ENPOINT + "/api/slideShow";
const API_PATH_Category = ConstantList.API_ENPOINT + "/api/category";
const API_PATH_Color = ConstantList.API_ENPOINT + "/api/color";
const API_PATH_Agency = ConstantList.API_ENPOINT + "/api/agency";
const API_PATH_Upload = ConstantList.API_ENPOINT + "/api/upload"
export const searchByPage = (searchObject) => {
  var url = API_PATH + "/searchByPage";
  return axios.post(url, searchObject);
};

export const searchByLocation = () => {
  var url = API_PATH + "/getLocationList";
  return axios.post(url);
};

export const searchByConversionType = () => {
  var url = API_PATH + "/getConversionType";
  return axios.post(url);
};

export const getAllCategory = () => {
  var url = API_PATH_Category + "/searchByPage";
  return axios.post(url, { pageIndex: 0, pageSize: 1000 });
};
export const getAllColor = () => {
  var url = API_PATH_Color + "/searchByPage";
  return axios.post(url, {pageSize: 1000, pageIndex: 0});
}
export const getByPage = (page, pageSize) => {
  var API_PATH = ConstantList.API_ENPOINT + "/api/product";
  var pageIndex = page + 1;
  var params = pageIndex + "/" + pageSize;
  var url = API_PATH + params;
  return axios.get(url);
};

export const changeIsShowOffer = productID => {
  return axios.post(API_PATH + "/changeIsShowOffer/" + productID);
};

export const getItemById = id => {
  var url = API_PATH + "/" + id;
  return axios.get(url);
};
export const deleteItem = id => {
  var url = ConstantList.API_ENPOINT + "/api/slideShow" + "/" + id;
  return axios.delete(url);
};
export const codeWasUsed = product => {
  return axios.post(API_PATH + "/check/codeWasUsed", product);
};
export const checkCode = (id, code) => {
  var API_PATH = ConstantList.API_ENPOINT + "/api/product";
  const config = { params: { id: id, code: code } };
  var url = API_PATH + "/checkCode";
  return axios.get(url, config);
};

export const addNewData = (product, file) => {
  return axios.post(ConstantList.API_ENPOINT + "/api/slideShow", product);
};

export const updateData = product => {
  return axios.put(ConstantList.API_ENPOINT + "/api/product/" + product.id, product);
};


export const getPageProductAddAgency = (searchObject, agencyID) => {
  var url = API_PATH + "/getPageProductAddAgency/" + agencyID;
  return axios.post(url, searchObject);
};

export const uploadImage = (file, id) => {
  const url = ConstantList.API_ENPOINT + "/api/upload/image";
  let formData = new FormData();
  formData.append('file', file);
  formData.append('productID', id);
  const config = {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  }
  return axios.post(url, formData, config)
}

export const editImageSlide = (file, id) => {
  const url = API_PATH_Upload + "/imageSlide";
  let formData = new FormData();
  formData.append('file', file);
  formData.append('slideShow', id);
  const config = {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  }
  return axios.put(url, formData, config)
}

export const createImageSlide = file => {
  const url = API_PATH_Upload + "/imageSlide";
  let formData = new FormData();
  formData.append('file', file);
  const config = {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  }
  return axios.post(url, formData, config)
}

export const getPageAgencyDontHaveProductThis = (searchObject) => {
  var url = API_PATH_Agency + "/getPageAgencyDontHaveProductThis";
  return axios.post(url, searchObject);
};

export const addProductToListAgency = (idList,productId) => {
  var url = API_PATH + "/addMultipleOfferToAgency/"+productId;
  return axios({
    url,
    method: "POST",
    data: idList,
  });
};

export const addOfferToAllAgency = (productId) => {
  var url = API_PATH + "/addOfferToAllAgency/" + productId;
  return axios.post(url);
};

