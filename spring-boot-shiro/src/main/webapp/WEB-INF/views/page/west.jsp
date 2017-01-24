<div class="col-sm-3 col-md-2 sidebar">
          <ul class="nav nav-sidebar">
            <li class="active"><a href="/index">Overview <span class="sr-only">(current)</span></a></li>
            <shiro:hasPermission name="Reports">
            	<li><a href="/reports">Reports</a></li>
            </shiro:hasPermission>
            <shiro:hasPermission name="Analytics">
            	<li><a href="#">Analytics</a></li>
            </shiro:hasPermission>
            <shiro:hasPermission name="Export">
            	<li><a href="#">Export</a></li>
            </shiro:hasPermission>
            <shiro:hasPermission name="Nav_item">
            	<li><a href="">Nav item</a></li>
            </shiro:hasPermission>
            <shiro:hasPermission name="Nav_item_again">
            	<li><a href="">Nav item again</a></li>
            </shiro:hasPermission>
            <shiro:hasPermission name="One_more_nav">
            	<li><a href="">One more nav</a></li>
            </shiro:hasPermission>
            <shiro:hasPermission name="Another_nav_item">
            	<li><a href="">Another nav item</a></li>
            </shiro:hasPermission>
            <shiro:hasPermission name="More_navigation">
            	<li><a href="">More navigation</a></li>
            </shiro:hasPermission>
            <shiro:hasPermission name="Acl_Manager">
            	<li><a href="/acl">Acl Manager</a></li>
            </shiro:hasPermission>
            <shiro:hasPermission name="One_more_nav1">
            	<li><a href="">One more nav</a></li>
            </shiro:hasPermission>
            <shiro:hasPermission name="Another_nav_item1">
           		<li><a href="">Another nav item</a></li>
            </shiro:hasPermission>
          </ul>
        </div>