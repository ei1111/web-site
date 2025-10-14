document.addEventListener("DOMContentLoaded", () => {
  const token = localStorage.getItem("accessToken");

  const loginMenu = document.getElementById("loginMenu");
  const logoutMenu = document.getElementById("logoutMenu");
  const mypageMenu = document.getElementById("mypageMenu");
  const registerMenu = document.getElementById("registerMenu");
  const itemListMenu = document.getElementById("itemListMenu");
  const orderListMenu = document.getElementById("orderListMenu");
  const adminMenu = document.getElementById("adminMenu");

  if (token) {
    // 로그인 상태이면 로그인 메뉴 숨기기, 로그아웃/마이페이지 보이기
    if (loginMenu) loginMenu.style.display = "none";
    if (logoutMenu) logoutMenu.style.display = "block";
    if (mypageMenu) mypageMenu.style.display = "block";
    if (registerMenu) registerMenu.style.display = "block";
    if (itemListMenu) itemListMenu.style.display = "block";
    if (orderListMenu) orderListMenu.style.display = "block";
    if (adminMenu) adminMenu.style.display = "block";
  } else {
    // 로그아웃 상태이면 로그인 메뉴 보이기, 로그아웃/마이페이지 숨기기
    if (loginMenu) loginMenu.style.display = "block";
    if (logoutMenu) logoutMenu.style.display = "none";
    if (mypageMenu) mypageMenu.style.display = "none";
    if (registerMenu) registerMenu.style.display = "none";
    if (itemListMenu) itemListMenu.style.display = "none";
    if (orderListMenu) orderListMenu.style.display = "none";
    if (adminMenu) adminMenu.style.display = "none";
  }
});

document.addEventListener("DOMContentLoaded", () => {
  const logoutBtn = document.getElementById("logoutBtn");
  if (logoutBtn) {
    logoutBtn.addEventListener("click", (e) => {
      e.preventDefault();          // 링크 기본 이동 방지
      localStorage.removeItem("accessToken");  // 토큰 삭제
      location.href = "/";        // 메인페이지 이동
    });
  }
});
