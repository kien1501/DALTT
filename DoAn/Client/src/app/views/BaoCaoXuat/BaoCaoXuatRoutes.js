import { EgretLoadable } from "egret";
import ConstantList from "../../appConfig";
import { useTranslation, withTranslation, Trans } from 'react-i18next';
const Agency = EgretLoadable({
  loader: () => import("./BaoCaoXuat")
});
const ViewComponent = withTranslation()(Agency);
const BaoCaoXuatRoutes = [
  {
    path: ConstantList.ROOT_PATH + "directory/BaoCaoXuat",
    exact: true,
    component: ViewComponent
  }
];

export default BaoCaoXuatRoutes;