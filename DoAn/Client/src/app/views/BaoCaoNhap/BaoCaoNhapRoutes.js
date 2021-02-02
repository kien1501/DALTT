import { EgretLoadable } from "egret";
import ConstantList from "../../appConfig";
import { useTranslation, withTranslation, Trans } from 'react-i18next';
const Agency = EgretLoadable({
  loader: () => import("./BaoCaoNhap")
});
const ViewComponent = withTranslation()(Agency);
const BaoCaoNhapRoutes = [
  {
    path: ConstantList.ROOT_PATH + "directory/BaoCaoNhap",
    exact: true,
    component: ViewComponent
  }
];

export default BaoCaoNhapRoutes;