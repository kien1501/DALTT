import axios from "axios";
import ConstantList from "../../appConfig";
const API_PATH = ConstantList.API_ENPOINT + "/public/file";
export const searchByPage = (dto) => {
  var url = API_PATH + "/search";
  return axios.post(url, dto);
};
export const getListFile = (pageIndex, pageSize) => {
  var url = API_PATH + "/"+pageIndex+"/"+pageSize;
  return axios.get(url);
};
export const uploadImage = (dto) => {
  var url = ConstantList.API_ENPOINT + "/public/image";
  return axios.post(url, dto);
};
export const getItemById = id => {
  var url = API_PATH + "/"+id;
  return axios.get(url);
};
export const deleteItem = id => {
  var url = API_PATH + "/delete/"+id;
  return axios.delete(url);
};
export const saveItem = (item) => {
  if(item && item.id ){
    const id = item.id;
    var url = API_PATH + "/update/"+id;
    return axios.put(url, item);
  }else{
    var url = API_PATH+'/create';
    return axios.post(url, item);
  }
};
export const checkSlug = (id,slug) => {
  const config = { params: {id: id, slug: slug } };
  var url = API_PATH + "/checkSlug";
  return axios.get(url,config);
};

export const fileUploadBlob=(blobInfo)=>{
  const url =ConstantList.API_ENPOINT+"/api/file/upload";
  let formData = new FormData();
  formData.append('uploadfile', blobInfo.blob(),blobInfo.filename());
  //formData.set('uploadfile',blobInfo)
  //formData.append('uploadfile',file);//Lưu ý tên 'uploadfile' phải trùng với tham số bên Server side
  const config = {
      headers: {
          'Content-Type': 'multipart/form-data'
        }
  }
  return  axios.post(url, formData,config)
}; 