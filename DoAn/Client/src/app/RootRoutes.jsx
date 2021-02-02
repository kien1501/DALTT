import React from "react";
import { Redirect } from "react-router-dom";
import homeRoutes from "./views/home/HomeRoutes";
import sessionRoutes from "./views/sessions/SessionRoutes";
import dashboardRoutes from "./views/dashboard/DashboardRoutes";
import administrativeUnitRoutes from "./views/AdministrativeUnit/AdministrativeUnitRoutes";
import otherRoutes from "./views/others/OtherRoutes";
import UserRoutes from "./views/User/UserRoutes";
import departmentRoutes from "./views/Department/DepartmentRoutes";
import roleRoutes from "./views/Role/RoleRoutes";
import ConstantList from "./appConfig";
// import MenuRoutes from "./views/Menus/MenuRoutes";
import pageLayoutRoutes from "./views/page-layouts/PageLayoutRoutees";
import DonViTinhRoutes from "./views/DonViTinh/DonViTinhRoutes";
import khoRoutes from "./views/Kho/KhoRoutes";
import SanPhamRoutes from "./views/SanPham/SanPhamRoutes";
import PhieuNhapKhoRoutes from "./views/PhieuNhapKho/PhieuNhapKhoRoutes";
import PhieuXuatKhoRoutes from "./views/PhieuXuatKho/PhieuXuatKhoRoutes";
import SlideshowRoutes from "./views/Slideshow/SlideshowRoutes";
import DanhMucSanPhamRoutes from "./views/DanhMucSanPham/DanhMucSanPhamRoutes";
import NhanvienRouters from "./views/NhanVien/NhanVienRoutes";
import MenuRoutes from "./views/Menus/MenuRoutes";
import ShiftWorkRouters from "./views/ShiftWork/ShiftWorkRouters";
import SupplierRoutes from "./views/NhaCungCap/SupplierRoutes";
import CustomerRoutes from "./views/Customer/CustomerRoutes"
import ColorRoutes from "./views/Color/ColorRoutes"
import DonHangRoutes from "./views/DonHang/DonHangRoutes";
import EventRoutes from "./views/Event/EventRoutes";
import BaoCaoNhapRoutes from "./views/BaoCaoNhap/BaoCaoNhapRoutes";
import BaoCaoTonRoutes from "./views/BaoCaoTon/BaoCaoTonRoutes";
import BaoCaoXuatRoutes from "./views/BaoCaoXuat/BaoCaoXuatRoutes";

const redirectRoute = [
  {
    path: ConstantList.ROOT_PATH,
    exact: true,
    component: () => <Redirect to={ConstantList.HOME_PAGE} /> //Luôn trỏ về HomePage được khai báo trong appConfig
  }
];

const errorRoute = [
  {
    component: () => <Redirect to={ConstantList.ROOT_PATH + "session/404"} />
  }
];

const routes = [
  ...homeRoutes,
  ...sessionRoutes,
  ...dashboardRoutes,
  ...administrativeUnitRoutes,
  ...departmentRoutes,
  ...pageLayoutRoutes,
  ...khoRoutes,
  ...SanPhamRoutes,
  ...PhieuNhapKhoRoutes,
  ...PhieuXuatKhoRoutes,
  ...SlideshowRoutes,
  ...DanhMucSanPhamRoutes,
  ...NhanvienRouters,
  ...MenuRoutes,
  ...UserRoutes,
  ...ShiftWorkRouters,
  ...SupplierRoutes,
  ...CustomerRoutes,
  ...ColorRoutes,
  ...DonHangRoutes,
  ...DonViTinhRoutes,
  ...EventRoutes,
  ...BaoCaoNhapRoutes,
  ...BaoCaoTonRoutes,
  ...BaoCaoXuatRoutes,
  ...errorRoute

];

export default routes;
