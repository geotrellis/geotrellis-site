# -*- mode: ruby -*-
# vi: set ft=ruby :

def local_ip
  `which ipconfig`

  if $?.exitstatus == 0
    `ipconfig getifaddr en0`
  else
    `ip -o -4 addr list eth0 | awk '{print $4}' | cut -d/ -f1`
  end.strip
end

Vagrant.require_version ">= 1.5"

VAGRANTFILE_API_VERSION = "2"

# Ensure role dependencies are in place
if [ "up", "provision" ].include?(ARGV.first) &&
  !(File.directory?("build/roles/azavea.git") || File.symlink?("build/roles/azavea.git")) ||
  !(File.directory?("build/roles/azavea.java") || File.symlink?("build/roles/azavea.java")) ||
  !(File.directory?("build/roles/azavea.nginx") || File.symlink?("build/roles/azavea.nginx")) ||
  !(File.directory?("build/roles/azavea.packer") || File.symlink?("build/roles/azavea.packer"))

  unless system("ansible-galaxy install --force -r build/roles.txt -p build/roles")
    $stderr.puts "\nERROR: Please install Ansible 1.4.2+ so that the ansible-galaxy binary"
    $stderr.puts "is available."
    exit(1)
  end
end

Vagrant.configure(VAGRANTFILE_API_VERSION) do |config|
  config.vm.box = "ubuntu/trusty64"

  config.vm.hostname = "geotrellis-site"

  config.vm.provider "virtualbox" do |v|
    v.memory = 1024
    v.cpus = 2
  end

  config.vm.network "forwarded_port", guest: 80, host: 8080

  # Wire up the proxy
  if Vagrant.has_plugin?("vagrant-proxyconf")
    config.proxy.http     = "http://#{local_ip}:8123/"
    config.proxy.https    = "http://#{local_ip}:8123/"
    config.proxy.no_proxy = "localhost,127.0.0.1"
  end

  config.vm.provision "ansible" do |ansible|
    ansible.playbook = "build/site.yml"
    ansible.groups = {
      "development" => [ "default" ]
    }
  end
end
