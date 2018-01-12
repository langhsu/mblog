
<ul class="list-group about-user">
    <li class="list-group-item user-card" >
        <div class="ava">
            <a href="${base}/users/${user.id}">
                <@showAva user.avatar "img-circle"/>
            </a>
        </div>
        <div class="user-info">
            <div class="nk mb10">${user.name}</div>
            <div class="mb6">
                <a class="btn btn-success btn-xs" href="${base}/user/profile"><i class="fa fa-pencil"></i> 修改账户</a>
            </div>
        </div>
    </li>
</ul>
<nav class="navbar navbar-default shadow-box background-white">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header visible-xs">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#home-navbar" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <span class="navbar-brand">导航</span>
        </div>
    </div>
    <div id="home-navbar" class="collapse navbar-collapse">
        <ul class="list-group user-nav first">
            <li class="list-group-item">
                <a href="${base}/user"><i class="icon icon-layers"></i> 动态</a>
            </li>
            <li class="list-group-item">
                <a href="${base}/user?method=posts"><i class="icon icon-list"></i> 我的文章</a>
            </li>
            <li class="list-group-item">
                <a href="${base}/user?method=comments"><i class="icon icon-speech"></i> 我的评论</a>
            </li>
        </ul>

        <ul class="list-group user-nav">
            <li class="list-group-item">
                <a href="${base}/user?method=favors"><i class="icon icon-heart"></i> 我的喜欢</a>
            </li>
            <li class="list-group-item">
                <a href="${base}/user?method=follows"><i class="icon icon-user-following"></i> 我的关注</a>
            </li>
            <li class="list-group-item">
                <a href="${base}/user?method=fans"><i class="icon icon-user-follow"></i> 我的粉丝</a>
            </li>
        </ul>

        <ul class="list-group user-nav">
            <li class="list-group-item">
                <a href="${base}/user?method=notifies">
                    <i class="icon icon-envelope"></i> 通知 <span class="label label-danger">${profile.badgesCount.notifies}</span>
                </a>
            </li>
        </ul>
    </div>
</nav>
