import { EgretLoadable } from "egret";
import ConstantList from "../../appConfig";
import { useTranslation, withTranslation, Trans } from 'react-i18next';
const RealStateType = EgretLoadable({
  loader: () => import("./Event")
});
const ViewComponent = withTranslation()(RealStateType);

const EventRoutes = [
  {
    path:  ConstantList.ROOT_PATH+"directory/event",
    exact: true,
    component: ViewComponent
  }
];

export default EventRoutes;