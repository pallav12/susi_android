package org.fossasia.susi.ai.skills.help

import android.os.Bundle
import android.view.View
import org.fossasia.susi.ai.R
import org.fossasia.susi.ai.helper.BaseFragment

class HelpFragment : BaseFragment() {
    override fun getTitle(): String {
        return getString(R.string.action_help)
    }

    override val rootLayout: Int
        get() = R.layout.fragment_help

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
    }
}
