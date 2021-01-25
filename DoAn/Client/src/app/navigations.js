import ConstantList from "./appConfig";
export const navigations = [
  {
    name: "Dashboard.dashboard",
    icon: "dashboard",
    path: ConstantList.ROOT_PATH + "dashboard/analytics",
    isVisible:true,
  },
  {
    name: "Dashboard.category",
    icon: "dashboard",
    path: "",
    isVisible:true,
    children: [
      {
        name: "Nhân Viên",
        path: ConstantList.ROOT_PATH+"directory/nhanvien",
        icon: "keyboard_arrow_right",
        isVisible:true,
      },
      {
        name: "Ca làm việc",
        path: ConstantList.ROOT_PATH+"salary/shiftwork",
        icon: "keyboard_arrow_right",
        isVisible:true,
      },
      {
        name: "Nhà cung cấp",
        path: ConstantList.ROOT_PATH+"directory/nhacungcap",
        icon: "keyboard_arrow_right",
        isVisible:true,
      },
      {
        name: "Sản phẩm",
        path: ConstantList.ROOT_PATH+"directory/sanpham",
        icon: "keyboard_arrow_right",
        isVisible:true,
      },
      {
        name: "Khách hàng",
        path: ConstantList.ROOT_PATH+"directory/customer",
        icon: "keyboard_arrow_right",
        isVisible:true,
      },
      {
        name: "Màu sắc",
        path: ConstantList.ROOT_PATH+"directory/color",
        icon: "keyboard_arrow_right",
        isVisible:true,
      }
    ]
  }  
  ,{
    name: "Dashboard.manage",
    isVisible:true,
    icon: "engineering",
    children: [
      {
        name: "Dashboard.eQAActivityLog",
        isVisible:true,
        path: ConstantList.ROOT_PATH + "user_manager/activity_log",
        icon: "keyboard_arrow_right"
      },
      {
        name: "manage.user",
        isVisible:true,
        path: ConstantList.ROOT_PATH + "user_manager/user",
        icon: "keyboard_arrow_right"
      },
      {
        name: "manage.menu",
        isVisible:true,
        path: ConstantList.ROOT_PATH + "list/menu",
        icon: "keyboard_arrow_right"
      }
    ]
  }
];
