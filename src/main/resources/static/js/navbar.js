document.addEventListener("DOMContentLoaded", () => {
  const token = localStorage.getItem("accessToken");

  const loginMenu = document.getElementById("loginMenu");
  const logoutMenu = document.getElementById("logoutMenu");
  const mypageMenu = document.getElementById("mypageMenu");
  const memberListMenu = document.getElementById("memberListMenu");
  const orderListMenu = document.getElementById("orderListMenu");
  const adminMenu = document.getElementById("adminMenu");
  const boardListMenu = document.getElementById("boardListMenu");
  const itemRegistMenu = document.getElementById("itemRegistMenu");
  const itemListMenu = document.getElementById("itemListMenu");

  if (token) {
    const payload = parseJwt(token);
    const role = payload?.role;
    console.log("payload", payload);
    console.log("role", role);

    // 로그인 상태이면 로그인 메뉴 숨기기, 로그아웃/마이페이지 보이기
    if (loginMenu) loginMenu.style.display = "none";
    if (logoutMenu) logoutMenu.style.display = "block";
    if (mypageMenu) mypageMenu.style.display = "block";

    if (role === "ADMIN") {
      if (memberListMenu) memberListMenu.style.display = "block";
    } else {
      if (memberListMenu) memberListMenu.style.display = "none";
    }

    if (itemListMenu) itemListMenu.style.display = "block";
    if (orderListMenu) orderListMenu.style.display = "block";
    if (adminMenu) adminMenu.style.display = "block";
    if (boardListMenu) boardListMenu.style.display = "block";
    if (itemRegistMenu) itemRegistMenu.style.display = "block";
  } else {
    // 로그아웃 상태이면 로그인 메뉴 보이기, 로그아웃/마이페이지 숨기기
    if (loginMenu) loginMenu.style.display = "block";
    if (logoutMenu) logoutMenu.style.display = "none";
    if (mypageMenu) mypageMenu.style.display = "none";
    if (memberListMenu) memberListMenu.style.display = "none";
    if (itemListMenu) itemListMenu.style.display = "none";
    if (orderListMenu) orderListMenu.style.display = "none";
    if (adminMenu) adminMenu.style.display = "none";
    if (boardListMenu) boardListMenu.style.display = "none";
    if (itemRegistMenu) itemRegistMenu.style.display = "none";
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


function parseJwt(token) {
  try {
    const base64Payload = token.split('.')[1]; // payload 부분만 추출
    const payload = atob(base64Payload);       // base64 → 문자열
    return JSON.parse(payload);               // JSON 객체 변환
  } catch (e) {
    return null;
  }
}