import { EgretLoadable } from "egret";
import ConstantList from "../../appConfig";
import { useTranslation, withTranslation, Trans } from 'react-i18next';
const Product = EgretLoadable({
  loader: () => import("./Slideshow")
});
const ViewComponent = withTranslation()(Product);
const SlideshowRoutes = [
  {
    path: ConstantList.ROOT_PATH+"directory/slideshow",
    exact: true,
    component: ViewComponent
  }
];

export default SlideshowRoutes;
