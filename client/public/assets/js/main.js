$(".btn-sign-up").click(function(){
    $(".sign-up-content").show();
    $(".sign-in-content").hide();
    $(".header-button-contain .active").removeClass("active");
    $(this).closest("div").addClass("active");
});
$(".btn-sign-in").click(function(){
    $(".sign-up-content").hide();
    $(".sign-in-content").show();
    $(".header-button-contain .active").removeClass("active");
    $(this).closest("div").addClass("active");
});