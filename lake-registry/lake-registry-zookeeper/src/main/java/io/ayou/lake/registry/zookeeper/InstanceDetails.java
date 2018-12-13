package io.ayou.lake.registry.zookeeper;

/**
 * @author yangs
 * 服务详情
 */
public class InstanceDetails {
    //服务名

    private String name;
    //版本
    private String version;
    //信息
    private String description;
    //服务提供端口
    private Integer port;
    //服务提供地址
    private String address;
    //当前注册路径
    private String path;
    //状态 0 正常 1 禁用 2 不正常
    private Integer status;

    public InstanceDetails() {
    }
    public InstanceDetails(String name, String version, String description, Integer port, String address, String path, Integer status) {
        this.name = name;
        this.version = version;
        this.description = description;
        this.port = port;
        this.address = address;
        this.path = path;
        this.status = status;
    }

    @Override
    public String toString() {
        return "InstanceDetails{" +
                "name='" + name + '\'' +
                ", version='" + version + '\'' +
                ", description='" + description + '\'' +
                ", port=" + port +
                ", address='" + address + '\'' +
                ", path='" + path + '\'' +
                ", status=" + status +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
