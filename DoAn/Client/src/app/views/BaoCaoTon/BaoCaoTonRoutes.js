import { EgretLoadable } from "egret";
import ConstantList from "../../appConfig";
import { useTranslation, withTranslation, Trans } from 'react-i18next';
const Agency = EgretLoadable({
  loader: () => import("./BaoCaoTon")
});
const ViewComponent = withTranslation()(Agency);
const BaoCaoTonRoutes = [
  {
    path: ConstantList.ROOT_PATH + "directory/BaoCaoTon",
    exact: true,
    component: ViewComponent
  }
];

export default BaoCaoTonRoutes;