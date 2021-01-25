import { EgretLoadable } from "egret";
import ConstantList from "../../appConfig";
import { useTranslation, withTranslation, Trans } from 'react-i18next';
const Customer = EgretLoadable({
  loader: () => import("./Customer")
});
const ViewComponent = withTranslation()(Customer);
const CustomerRoutes = [
  {
    path: ConstantList.ROOT_PATH + "directory/customer",
    exact: true,
    component: ViewComponent
  }
];

export default CustomerRoutes;