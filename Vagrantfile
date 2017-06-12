# -*- mode: ruby -*-
# vi: set ft=ruby :

Vagrant.configure("2") do |config|

    # Ubuntu 14.04 LTS
    config.vm.box = "ubuntu/trusty64"

    # Ports to the services
    config.vm.network :forwarded_port, guest: 8080, host: 8080  # nginx
    config.vm.network :forwarded_port, guest: 9090, host: 9090  # gtsite-service

    # VM resource settings
    config.vm.provider :virtualbox do |vb|
        vb.memory = 2048
        vb.cpus = 1
    end

    config.vm.synced_folder "~/.aws", "/home/vagrant/.aws"
    config.vm.synced_folder "./", "/home/vagrant/geotrellis-site"

    config.vm.provision "shell" do |s|
        s.inline = <<-SHELL
            sed -i 's/^mesg n$/tty -s \\&\\& mesg n/g' /root/.profile
        SHELL
    end

    # Provisioning
    # Ansible is installed automatically by Vagrant.
    config.vm.provision "ansible_local" do |ansible|
        ansible.playbook = "deployment/ansible/playbook.yml"
        ansible.galaxy_role_file = "deployment/ansible/roles.yml"
        ansible.galaxy_roles_path = "deployment/ansible/roles"
    end

    config.vm.provision "shell" do |s|
        s.path = 'deployment/vagrant/cd_shared_folder.sh'
        s.args = "/home/vagrant/geotrellis-site"
    end

end
