package felipe.pereira.war_of_suits.view.game.roundsresult

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import felipe.pereira.war_of_suits.R
import felipe.pereira.war_of_suits.view.game.model.RoundResultViewEntity
import kotlinx.android.synthetic.main.item_round.view.*

class RoundsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: ArrayList<RoundResultViewEntity> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        RoundsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_round, parent, false)
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) = (holder as RoundsViewHolder).bind(items[position])

    fun setItems(rounds: List<RoundResultViewEntity>) {
        items.addAll(rounds)
        notifyDataSetChanged()
    }

    inner class RoundsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: RoundResultViewEntity) {  //An improvement in view can be put the round number
            with(itemView) {
                roundWinnerTextView.text = item.winner.name

                magnetoTextView.text = String.format(
                    context.getString(R.string.card_played),
                    context.getString(R.string.magneto),
                    "${item.magnetoCard.number}",
                    item.magnetoCard.suit.name)

                professorTextView.text = String.format(
                    context.getString(R.string.card_played),
                    context.getString(R.string.professor),
                    "${item.professorCard.number}",
                    item.professorCard.suit.name)
            }
        }
    }
}