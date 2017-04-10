nginx profile:
1.modify nginx.conf;
3.put the project into the folder of 'html';
4.startup nginx;
ps:nginx download url:http://nginx.org/en/download.html
nginx cmd:
1. start nginx;
2. nginx -s reload;
3. nginx -s stop;

e.g.nginx profile:

server {
    #listen       80;
    listen      8080;
    server_name  localhost;

    #charset koi8-r;
    #access_log  /var/log/nginx/log/host.access.log  main;

    ####################### cms  #############################################

    location /cms/ {
        proxy_pass   http://10.10.10.91/;
    }

    ####################### vrs #############################################

    location /vrs/ {
        proxy_pass   http://10.10.10.92/;
    }


    ####################### uc #############################################

    location /clotho-management/uc_management/ {
        proxy_pass   http://10.10.10.221:8080/uc_management/;
    }


    ####################### metis ###########################################

#    location /metis/ {
#        proxy_pass   http://192.168.20.161:8080/metis-schemaregister/metis/;
#    }


#    location /clotho-management/metis/ {
#        proxy_pass   http://192.168.20.161:8080/metis-schemaregister/metis/;
#    }

    ####################### cdn #############################################

    location /clotho-management/cdn-management/ {
       proxy_pass   http://10.10.10.168:9090/cdn-management/;
       #proxy_pass   http://192.168.66.105:8080/;
       #proxy_pass   http://192.168.20.119:8080/;
    }

    ####################### upload ##########################################
    #location /clotho-management/upload/ {
    #    proxy_pass   http://m.clotho.d3dstore.com/clotho-management/upload/;
    #}

    ####################### clotho management ###############################
    location /clotho-management/ {
        proxy_pass   http://14.17.86.210:28888/clotho-management/;
    }

    ####################### bigdata view ###############################
#    location /resources/ {
#        proxy_pass   http://192.168.20.185:8080/resources/;
#    }
    location / {
    	#your html directory
        root   /usr/share/nginx/html;
        index  index.html index.htm;
    }
}
