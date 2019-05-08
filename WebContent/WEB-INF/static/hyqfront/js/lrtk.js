function jQuery(e) {return document.getElementById(e);}

document.getElementsByClassName = function(cl) {
    var retnode = [];
    var myclass = new RegExp('\\b'+cl+'\\b');
    var elem = this.getElementsByTagName('*');
    for (var i = 0; i < elem.length; i++) {
        var classes = elem[i].className;
        if (myclass.test(classes)) retnode.push(elem[i]);
    }
    return retnode;
}
var MyMar;
var speed = 17; //
var spec = 3; //
var ipath = 'images/'; //
/*var thumbs = document.getElementsByClassName('thumb_img');
for (var i=0; i<thumbs.length; i++) {
    thumbs[i].onmouseover = function () {jQuery('main_img').src=this.rel; jQuery('main_img').link=this.link;};
    thumbs[i].onmouseover = function () {location = this.link}
}*/
/*jQuery('main_img').click = function () {location = this.link;}*/
jQuery('gotop').onmouseover = function() {this.src = ipath + 'xqtop.jpg'; MyMar=setInterval(gotop,speed);}
jQuery('gotop').onmouseout = function() {this.src = ipath + 'xqtop.jpg'; clearInterval(MyMar);}
jQuery('gobottom').onmouseover = function() {this.src = ipath + 'xqbottom.jpg'; MyMar=setInterval(gobottom,speed);}
jQuery('gobottom').onmouseout = function() {this.src = ipath + 'xqbottom.jpg'; clearInterval(MyMar);}
function gotop() {jQuery('showArea').scrollTop-=spec;}
function gobottom() {jQuery('showArea').scrollTop+=spec;}