import { EgretLoadable } from "egret";
import ConstantList from "../../appConfig";
import { useTranslation, withTranslation, Trans } from 'react-i18next';
const Agent = EgretLoadable({
  loader: () => import("./SanPham")
});
const ViewComponent = withTranslation()(Agent);
const SanPhamRoutes = [
  {
    path: ConstantList.ROOT_PATH + "directory/sanpham",
    exact: true,
    component: ViewComponent
  }
];

export default SanPhamRoutes;