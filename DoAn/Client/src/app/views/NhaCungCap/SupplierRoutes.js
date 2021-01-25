import { EgretLoadable } from "egret";
import ConstantList from "../../appConfig";
import { useTranslation, withTranslation, Trans } from 'react-i18next';
const Agency = EgretLoadable({
  loader: () => import("./NhaCungCap")
});
const ViewComponent = withTranslation()(Agency);
const SupplierRoutes = [
  {
    path: ConstantList.ROOT_PATH + "directory/nhacungcap",
    exact: true,
    component: ViewComponent
  }
];

export default SupplierRoutes;