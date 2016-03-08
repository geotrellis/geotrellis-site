# -*- mode: ruby -*-
# vi: set ft=ruby :

Vagrant.require_version ">= 1.6"

if ["up", "provision", "status"].include?(ARGV.first)
  require_relative "vagrant/ansible_galaxy_helper"

  AnsibleGalaxyHelper.install_dependent_roles("deployment/ansible")
end

Vagrant.configure("2") do |config|
  config.vm.box = "ubuntu/trusty64"
  config.vm.hostname = "geotrellis-site"

  # Wire up package caching
  if Vagrant.has_plugin?("vagrant-cachier")
    config.cache.scope = :machine
  end

  config.vm.provider "virtualbox" do |v|
    v.memory = 2048
    v.cpus = 2
  end

  config.vm.network "forwarded_port", guest: 80, host: 8080

  config.vm.synced_folder ".", "/opt/geotrellis-site"

  config.vm.provision "ansible" do |ansible|
    ansible.playbook = "deployment/ansible/site.yml"
    ansible.groups = {
      "development" => [ "default" ]
    }
  end
end
