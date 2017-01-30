FROM nginx:1.10.2

COPY _site/ /var/www/

# Overwrite default nginx configuration
COPY nginx.conf /etc/nginx/

EXPOSE 8080
