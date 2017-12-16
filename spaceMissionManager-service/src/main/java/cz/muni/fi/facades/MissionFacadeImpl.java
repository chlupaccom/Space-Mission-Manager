package cz.muni.fi.facades;

import cz.muni.fi.dto.MissionCreateDTO;
import cz.muni.fi.dto.MissionDTO;
import cz.muni.fi.entity.Mission;
import cz.muni.fi.facade.MissionFacade;
import cz.muni.fi.services.BeanMappingService;
import cz.muni.fi.services.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class MissionFacadeImpl implements MissionFacade {

    @Autowired
    BeanMappingService beanMappingService;

    @Autowired
    MissionService missionService;

	@Override
	public void archive(MissionDTO mission, LocalDate endDate) {
		missionService.archive(beanMappingService.mapTo(mission, Mission.class), endDate);
	}

	@Override
    public Long createMission(MissionCreateDTO mission) {
        Mission mappedMission = beanMappingService.mapTo(mission, Mission.class);
        missionService.createMission(mappedMission);
        return mappedMission.getId();
    }

    @Override
    public void cancelMission(MissionDTO mission) {
        Mission mappedMission = beanMappingService.mapTo(mission, Mission.class);
        missionService.cancelMission(mappedMission);
    }

    @Override
    public List<MissionDTO> findAllMissions() {
        return beanMappingService.mapTo(missionService.findAllMissions(), MissionDTO.class);
    }

    @Override
    public List<MissionDTO> findAllMissions(boolean active) {
        return beanMappingService.mapTo(missionService.findAllMissions(active), MissionDTO.class);
    }

    @Override
    public MissionDTO findMissionById(Long id) {
        return beanMappingService.mapTo(missionService.findMissionById(id), MissionDTO.class);
    }

    @Override
    public void updateMission(MissionDTO mission) {
        missionService.updateMission(beanMappingService.mapTo(mission, Mission.class));
    }
}
