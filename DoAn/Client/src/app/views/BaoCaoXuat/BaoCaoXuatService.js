import axios from "axios";
import ConstantList from "../../appConfig";

const API_PATH = ConstantList.API_ENPOINT + "/api/phieuxuatkho/";
const API_PATHKHO = ConstantList.API_ENPOINT + "/api/sanphamkho/";
export const searchByPage = (searchObject) => {
  return axios.post(API_PATH +  "baoCao", searchObject);
};
export const exportToExcel = (searchObject) => {
  return axios({
    method: 'post',
    data: searchObject,
    url: ConstantList.API_ENPOINT + "/api/fileDownload/baocaoxuat",
    responseType: 'blob',
  })
}

