package eu.automateeverything.tabletsplugin

import eu.automateeverything.data.hardware.PortValue
import eu.automateeverything.data.localization.Resource
import java.math.BigDecimal

class DataPortValue: PortValue {
    override fun toFormattedString(): Resource {
        return R.n_a
    }

    override fun asDecimal(): BigDecimal {
        return BigDecimal.ZERO
    }
}