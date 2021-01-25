import { EgretLoadable } from "egret";
import ConstantList from "../../appConfig";
import { useTranslation, withTranslation, Trans } from 'react-i18next';
const Agency = EgretLoadable({
  loader: () => import("./DanhMucSanPham")
});
const ViewComponent = withTranslation()(Agency);
const DanhMucSanPhamRoutes = [
  {
    path: ConstantList.ROOT_PATH + "directory/DanhMucSanPham",
    exact: true,
    component: ViewComponent
  }
];

export default DanhMucSanPhamRoutes;