import { EgretLoadable } from "egret";
import ConstantList from "../../appConfig";
import { useTranslation, withTranslation, Trans } from 'react-i18next';
const RealStateType = EgretLoadable({
  loader: () => import("./PhieuXuatKho")
});
const ViewComponent = withTranslation()(RealStateType);

const PhieuXuatKhoRoutes = [
  {
    path:  ConstantList.ROOT_PATH+"directory/phieuxuatkho",
    exact: true,
    component: ViewComponent
  }
];

export default PhieuXuatKhoRoutes;