import { EgretLoadable } from "egret";
import ConstantList from "../../appConfig";
import { useTranslation, withTranslation, Trans } from 'react-i18next';
const Agency = EgretLoadable({
  loader: () => import("./DonHang")
});
const ViewComponent = withTranslation()(Agency);
const DonHangRoutes = [
  {
    path: ConstantList.ROOT_PATH + "directory/donHang",
    exact: true,
    component: ViewComponent
  }
];

export default DonHangRoutes;