import { EgretLoadable } from "egret";
import ConstantList from "../../appConfig";
import { useTranslation, withTranslation, Trans } from 'react-i18next';
const RealStateType = EgretLoadable({
  loader: () => import("./PhieuNhapKho")
});
const ViewComponent = withTranslation()(RealStateType);

const PhieuNhapKhoRoutes = [
  {
    path:  ConstantList.ROOT_PATH+"directory/phieunhapkho",
    exact: true,
    component: ViewComponent
  }
];

export default PhieuNhapKhoRoutes;