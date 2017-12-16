package cz.muni.fi;


import cz.muni.fi.config.ServiceConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Configuration
@Import(ServiceConfiguration.class)
public class ManagerWithSamplesConfig {

	final static Logger log = LoggerFactory.getLogger(ManagerWithSamplesConfig.class);

	@Autowired
	private SampleDataLoadingFacade sampleDataLoadingFacade;

	@PostConstruct
	public void dataLoading() throws IOException {
		log.debug("dataLoading()");
		sampleDataLoadingFacade.loadData();
	}
}
