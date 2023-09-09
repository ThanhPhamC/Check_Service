package org.rikkei;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import static org.rikkei.Constant.SERVICE_NAME;
import static org.rikkei.Constant.SERVICE_STATUS;

public class Service {
    private String serviceName;

    public Service() {
    }

    public Service(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Service checkService() throws IOException {
        Process process = Runtime.getRuntime().exec("sc query " + this.serviceName);
        Scanner reader = new Scanner(process.getInputStream(), "UTF-8");
        boolean checkStatus = true;
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            if (line.contains(SERVICE_STATUS)) {
                this.serviceName += ".exe";
                checkStatus = false;
                break;
            }
        }
        if (checkStatus) {
            return null;
        }
        return this;
    }

    protected static List<Service> getListService(Properties props) {
        String serviceNames = props.getProperty(SERVICE_NAME);
        List<String> services = Arrays.asList(serviceNames.split(","));
        return services.stream()
                .map(serviceName -> {
                    try {
                        return new Service(serviceName).checkService();
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
