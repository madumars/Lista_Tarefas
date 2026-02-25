package com.example.projetolistadetarefas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.graphics.Paint
import android.graphics.Color

class TarefaAdapter(
    private val lista: MutableList<Tarefa>,
    private val onClick: (Tarefa) -> Unit,
    private val onStatusChange: (Tarefa) -> Unit
) : RecyclerView.Adapter<TarefaAdapter.TarefaViewHolder>() {

    inner class TarefaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val check: CheckBox = itemView.findViewById(R.id.checkTarefa)
        val texto: TextView = itemView.findViewById(R.id.tvTarefa)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarefaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tarefa, parent, false)
        return TarefaViewHolder(view)
    }

    override fun onBindViewHolder(holder: TarefaViewHolder, position: Int) {
        val tarefa = lista[position]

        holder.texto.text = tarefa.descricao
        
        // Remove o listener antes de setar o estado para não disparar o evento
        holder.check.setOnCheckedChangeListener(null)
        holder.check.isChecked = tarefa.concluida

        atualizarEstilo(holder, tarefa)

        holder.check.setOnCheckedChangeListener { _, isChecked ->
            tarefa.concluida = isChecked
            atualizarEstilo(holder, tarefa)
            // Avisa a MainActivity que o status mudou para ela mover o item
            onStatusChange(tarefa)
        }

        holder.itemView.setOnClickListener {
            onClick(tarefa)
        }

        holder.itemView.setOnLongClickListener {
            val pos = holder.adapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                lista.removeAt(pos)
                notifyItemRemoved(pos)
            }
            true
        }
    }

    override fun getItemCount() = lista.size

    private fun atualizarEstilo(holder: TarefaViewHolder, tarefa: Tarefa) {
        if (tarefa.concluida) {
            holder.texto.paintFlags = holder.texto.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            holder.texto.setTextColor(Color.parseColor("#4CAF50"))
        } else {
            holder.texto.paintFlags = holder.texto.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            holder.texto.setTextColor(Color.BLACK)
        }
    }
}
