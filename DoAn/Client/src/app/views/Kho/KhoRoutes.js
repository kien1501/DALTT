import { EgretLoadable } from "egret";
import ConstantList from "../../appConfig";
import { useTranslation, withTranslation, Trans } from 'react-i18next';
const RealEstateSourceTable = EgretLoadable({
  loader: () => import("./KhoTable")
});
const ViewComponent = withTranslation()(RealEstateSourceTable);

const khoRoutes = [
  {
    path:  ConstantList.ROOT_PATH+"directory/kho",
    exact: true,
    component: ViewComponent
  }
];

export default khoRoutes;