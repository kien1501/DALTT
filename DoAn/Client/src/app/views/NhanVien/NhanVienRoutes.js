import { EgretLoadable } from "egret";
import ConstantList from "../../appConfig";
import { useTranslation, withTranslation, Trans } from 'react-i18next';
const UrbanAreaTable = EgretLoadable({
  loader: () => import("./NhanVienTable")
});
const ViewComponent = withTranslation()(UrbanAreaTable);

const NhanVienRoutes = [
  {
    path:  ConstantList.ROOT_PATH+"directory/nhanvien",
    exact: true,
    component: ViewComponent
  }
];

export default NhanVienRoutes;
