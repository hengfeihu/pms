package com.hfh.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.hfh.utils.PropertiesUtil;
import io.ebean.EbeanServerFactory;
import io.ebean.config.ServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class DataSourceManager {

    private final static Logger logger = LoggerFactory.getLogger(DataSourceManager.class);

    private DruidDataSource druidDataSource;
    private Properties dataSourceProperties;

    public DataSourceManager() {
        this.dataSourceProperties = loadProperties();
    }

    private Properties loadProperties() {
        PropertiesUtil propertiesUtil = new PropertiesUtil("/jdbc.properties");
        Map<?, ?> properties = propertiesUtil.getAllProperty();
        Properties dbProperties = null;
        for (Map.Entry entry : properties.entrySet()) {
            String entryKey = (String) entry.getKey();
            if (entryKey.startsWith("druid.")) {
                if (dbProperties == null) {
                    dbProperties = new Properties();
                }
                String entryValue = (String) entry.getValue();
                dbProperties.put(entryKey, entryValue);
            }
        }
        if (dbProperties != null) {
            setDataSourceProperties(dbProperties);
        }
        return dbProperties;
    }

    public void init() {
        if (druidDataSource == null) {
            druidDataSource = new DruidDataSource();
            if (dataSourceProperties != null) {
                druidDataSource.configFromPropety(dataSourceProperties);
                try {
                    logger.info(DruidDataSourceFactory.PROP_URL + ":" + druidDataSource.getUrl());
                    logger.info(DruidDataSourceFactory.PROP_USERNAME + ":" + druidDataSource.getUsername());
                    logger.info(DruidDataSourceFactory.PROP_PASSWORD + ":" + druidDataSource.getPassword());
                    druidDataSource.init();
                    ServerConfig serverConfig = new ServerConfig();
                    serverConfig.setDataSource(druidDataSource);
                    serverConfig.setDdlGenerate(true);
                    serverConfig.setDdlRun(true);
                    List<String> packages = new ArrayList<>();
                    packages.add("com.hfh.model");
                    serverConfig.setPackages(packages);
                    EbeanServerFactory.create(serverConfig);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setDataSourceProperties(Properties properties) {
        this.dataSourceProperties = properties;
    }
}
