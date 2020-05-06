$(function () {
    let menu_width = 290;
    let menu = $(".menu");
    let menu_open = $(".menu-open");
    let menu_close = $(".menu-close");
    let overlay = $(".overlay");
  
    menu_open.click(function (e) {
      e.preventDefault();
      menu.css({"right": "0px"});
      overlay.css({"opacity": "1", "width": "100%"});
    });
    
    menu_close.click(function (e) {
      e.preventDefault();
      menu.css({"right": "-" + menu_width + "px"});
      overlay.css({"opacity": "0", "width": "0"});
    });
  });



  
function injectMenu(){

	let element = document.getElementById('sidebar');
		element.innerHTML =
	`

	<div class="overlay"></div>

	<div class="menu">
		<a href="#" class="menu-close">&times;</a>
		<ul>
			<li><i class="fas fa-tachometer-alt"></i><a href="../html/dashboard.html" > DashBoard</a></li>
			<li><i class="fas fa-user"></i><a href="../html/my-profile.html" > My Profile</a></li>
			<li><i class="fas fa-home"></i><a href="../html/my-rooms.html" > My Rooms</a></li>
			<li><i class="fas fa-cogs"></i><a href="../html/new-room.html" > New Room</a></li>
			<li><i class="fas fa-users"></i><a href="../html/players-search.html" > Search Players</a></li>
			<li><i class="fas fa-search"></i><a href="../html/room-search.html" > Search Rooms</a></li>
			<li><i class="fas fa-bolt"></i><a href="../html/be-a-pro.html" > Be a PRO!</a></li>
		</ul>
	</div>

	`;
}
injectMenu();