package com.ClinicBackend.demo.ManageFiles.DBWork

import com.ClinicBackend.demo.Entities.Department
import com.ClinicBackend.demo.Entities.ManagePositions.CurrentPosition
import com.ClinicBackend.demo.Entities.ManagePositions.PositionToBuy
import com.ClinicBackend.demo.Repos.CurrentPositionRepos
import com.ClinicBackend.demo.Repos.PositionToBuyRepos
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class CurrentPositionsService {
    @Autowired
    lateinit var currentPositionRepos: CurrentPositionRepos

    @Autowired
    lateinit var positionToBuyRepos:PositionToBuyRepos

    fun getCurrentPositionsOfDepartment(department: Department):List<CurrentPosition>{
        return department.currentPositions.toList()
    }

    fun checkPositionToBuy(currentPosition: CurrentPosition){
        val now = LocalDate.now()
        val currentLimits=currentPosition.limits.first { it.startDate!!<=now && now<=it.endDate!!}
        if(currentPosition.count!!<currentLimits.min!!){
            var positionToBuy=positionToBuyRepos.findByCurrentPosition(currentPosition)
            if(positionToBuy!=null)positionToBuy.countToBuy=currentLimits.max!!-currentPosition.count!!
            else positionToBuy = PositionToBuy().also {
                it.countToBuy=currentLimits.max!!-currentPosition.count!!
                it.currentPosition=currentPosition
            }
            positionToBuyRepos.save(positionToBuy)
        }
    }
}