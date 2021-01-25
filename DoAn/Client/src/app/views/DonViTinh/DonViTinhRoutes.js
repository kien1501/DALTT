import { EgretLoadable } from "egret";
import ConstantList from "../../appConfig";
import { useTranslation, withTranslation, Trans } from 'react-i18next';
const Agency = EgretLoadable({
  loader: () => import("./DonViTinh")
});
const ViewComponent = withTranslation()(Agency);
const DonViTinhRoutes = [
  {
    path: ConstantList.ROOT_PATH + "directory/donvitinh",
    exact: true,
    component: ViewComponent
  }
];

export default DonViTinhRoutes;