package codein.back.biz.manual;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("manualService")
public class ManualService {

    @Autowired
    private ManualDAO manualDAO;

    public List<ManualDTO> selectAll(ManualDTO manualDTO){
        return manualDAO.selectAll(manualDTO);
    }

    public ManualDTO selectOne(ManualDTO manualDTO){
        return manualDAO.selectOne(manualDTO);
    }

    public boolean insert(ManualDTO manualDTO){
        return manualDAO.insert(manualDTO);
    }

    public boolean update(ManualDTO manualDTO){
        return manualDAO.update(manualDTO);
    }

    public boolean delete(ManualDTO manualDTO){
        return manualDAO.delete(manualDTO);
    }

}
