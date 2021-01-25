import axios from "axios";
import ConstantList from "../../appConfig";

const API_PATH = ConstantList.API_ENPOINT + "/api/sanpham/";
const API_PATHKHO = ConstantList.API_ENPOINT + "/api/sanphamkho/";
export const searchByPage = (searchObject) => {
  return axios.post(API_PATH +  "searchByPage", searchObject);
};

export const deleteItem = id => {
  return axios.delete(API_PATH + id);
};

export const handleDeleteList = listAlert => {
  return axios.delete(API_PATH,listAlert );
};

export const saveItem = item => {
  return axios.post(API_PATH, item);
};
export const updateItem = item => {
  return axios.put(API_PATH +item.id, item);
};

export const getItemById = id => {
  return axios.get(API_PATH + id);
};

export const checkCode = (id, code) => {
  const config = { params: {id: id, code: code } };
  return axios.get(API_PATH + "checkCode", config);
};

export const uploadImage = (formData) => {
  //var url = ConstantList.API_ENPOINT + "/public/image";
  var url = ConstantList.API_ENPOINT + "/api/dafile/image";
  return axios.post(url, formData);

  // const url =ConstantList.API_ENPOINT+"/api/file/upload";
  // const config = {
  //     headers: {
  //         'Content-Type': 'multipart/form-data'
  //       }
  // }
  // return  axios.post(url, formData,config)

};
export const searchByPageK = (searchObject) => {
  return axios.post(API_PATHKHO +  "searchByPage", searchObject);
};